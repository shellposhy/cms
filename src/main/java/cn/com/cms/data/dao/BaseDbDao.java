package cn.com.cms.data.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cn.com.cms.common.DbData;
import cn.com.cms.common.DbTable;
import cn.com.cms.common.FieldCodes;
import cn.com.cms.common.CmsData;
import cn.com.cms.data.constant.EDataTransErrPolicy;
import cn.com.cms.data.model.DataField;
import cn.com.cms.data.model.DataTable;
import cn.com.cms.framework.base.Result;
import cn.com.cms.library.constant.EDataStatus;
import cn.com.cms.library.constant.EDataType;
import cn.com.people.data.util.DateTimeUtil;
import cn.com.people.data.util.StringUtil;

/**
 * 数据库操作基础服务类
 * 
 * @author shishb
 * @version 1.0
 */
public class BaseDbDao {
	private static final Logger logger = Logger.getLogger(BaseDbDao.class);
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 创建表
	 * 
	 * @param dbTable
	 *            表
	 */
	public void createTable(DbTable dbTable) {
		logger.info("====>创建表");
		StringBuilder sql = new StringBuilder("create table ").append(dbTable.getName()).append(" (");
		List<String> uniqList = new ArrayList<String>();

		for (DataField df : dbTable.getFieldList()) {
			sql.append(this.getFieldSql(df)).append(",");
			if (df.isUniq() && !FieldCodes.ID.equals(df.getCode())) {
				uniqList.add(df.getCode());
			}
		}
		sql.append("PRIMARY KEY (").append(FieldCodes.ID).append(")");
		for (String c : uniqList) {
			sql.append(",UNIQUE KEY AK_UNIQUE_KEY_").append(c).append(" (").append(c).append(")");
		}
		sql.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		jdbcTemplate.execute(sql.toString());
	}

	/**
	 * 删除表
	 * 
	 * @param tableName
	 */
	public void dropTable(String tableName) {
		jdbcTemplate.execute("drop table if exists " + tableName);
	}

	/**
	 * 修改表
	 * 
	 * @param table
	 */
	public void alterTable(DbTable table) {
		StringBuilder sql1 = new StringBuilder();
		List<DataField> fieldList = null;
		// 删除
		fieldList = table.getDrops();
		if (fieldList != null) {
			for (int i = 0; i < fieldList.size(); i++) {
				if (i > 0) {
					sql1.append(",");
				}
				sql1.append("Drop ").append(fieldList.get(i).getCode());
			}
		}
		// 修改
		fieldList = table.getChanges();
		if (fieldList != null) {
			for (int i = 0; i < table.getChanges().size(); i++) {
				if (sql1.length() > 0) {
					sql1.append(",");
				}
				sql1.append("CHANGE ").append(table.getChanges().get(i).getOldCode()).append(" ")
						.append(getFieldSql(table.getChanges().get(i)));
			}
		}
		// 新增
		fieldList = table.getAdds();
		for (int i = 0; i < fieldList.size(); i++) {
			if (sql1.length() > 0) {
				sql1.append(",");
			}
			sql1.append("ADD ").append(getFieldSql(fieldList.get(i)));
		}
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < table.getNames().size(); i++) {
			sql.append("ALTER TABLE ").append(table.getNames().get(i)).append(" ").append(sql1).append(";");

		}
		String ss = sql.toString();
		if (null != ss && !ss.isEmpty()) {
			jdbcTemplate.execute(ss);
		}

	}

	/**
	 * 插入单条数据
	 * 
	 * @param tableName
	 * @param doc
	 * @return 主键
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public Integer insert(final String tableName, final DbData doc) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement statement = null;
				if (doc.hasBlob()) {
					statement = getPreparedInsert(connection, tableName, doc);
					try {
						setPreparedInsert(statement, doc);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				} else {
					String sql = getStaticInsertSql(tableName, new DbData[] { doc });
					statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				}
				return statement;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 插入多条数据
	 * 
	 * @param tableName
	 * @param dataList
	 * @param errPolicy
	 * @return
	 */
	public Result<Integer> insert(final String tableName, final List<DbData> dataList,
			final EDataTransErrPolicy errPolicy) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Result<Integer> result = new Result<Integer>();
		List<Integer> idList = new ArrayList<Integer>(dataList.size());
		result.setList(idList);
		int total = 0;
		for (final DbData data : dataList) {
			int insertCount = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					// 需要回滚
					if (errPolicy == EDataTransErrPolicy.Rollback) {
						conn.setAutoCommit(false);
					}
					PreparedStatement st = getPreparedInsert(conn, tableName, dataList.get(0));
					try {
						setPreparedInsert(st, data);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (!conn.getAutoCommit()) {
						conn.commit();
					}
					return st;
				}
			}, keyHolder);
			if (errPolicy == EDataTransErrPolicy.Ignore) {
				total += insertCount;
				if (insertCount != 0)
					idList.add(keyHolder.getKey().intValue());
				else
					idList.add(0);
			} else {
				if (insertCount != 0)
					idList.add(keyHolder.getKey().intValue());
			}
		}
		if (errPolicy == EDataTransErrPolicy.Ignore)
			result.setTotalCount(total);
		else
			result.setTotalCount(idList.size());

		return result;
	}

	/**
	 * 插入多条数据，不获取id
	 * 
	 * @param tableName
	 * @param dataList
	 * @param errPolicy
	 * @return
	 */
	public Result<Integer> insertOnly(final String tableName, final List<DbData> dataList,
			final EDataTransErrPolicy errPolicy) {
		Result<Integer> result = new Result<Integer>();
		int total = 0;
		for (final DbData data : dataList) {
			int insertCount = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					// 需要回滚
					if (errPolicy == EDataTransErrPolicy.Rollback) {
						conn.setAutoCommit(false);
					}
					PreparedStatement st = getPreparedInsert(conn, tableName, dataList.get(0));
					try {
						setPreparedInsert(st, data);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (!conn.getAutoCommit()) {
						conn.commit();
					}
					return st;
				}
			});
			total += insertCount;

		}
		result.setTotalCount(total);
		return result;
	}

	/**
	 * 更新数据
	 * 
	 * @param tableName
	 * @param doc
	 */
	public void update(final String tableName, final DbData doc) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				// 有大字段
				PreparedStatement st = null;
				if (doc.hasBlob()) {
					st = getPreparedUpdate(conn, tableName, doc);
					setPreparedUpdate(st, doc);
				} // 没有大字段
				else {
					String sql = getStaticUpdateSql(tableName, doc);
					st = conn.prepareStatement(sql);
				}
				return st;
			}
		});
	}

	/**
	 * 删除单条数据
	 * 
	 * @param tableName
	 * @param dataId
	 */
	public void delete(final String tableName, final Integer dataId) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				StringBuilder sql = new StringBuilder("delete from ").append(tableName).append(" where ")
						.append(FieldCodes.ID).append("=").append(dataId);
				String delete = sql.toString();
				PreparedStatement st = null;
				st = conn.prepareStatement(delete);
				return st;
			}
		});
	}

	/**
	 * 根据查询语句删除
	 * 
	 * @param tableName
	 * @param where
	 * @return
	 */
	public Integer delete(final String tableName, final String where) {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				StringBuilder sql = new StringBuilder("delete from ").append(tableName).append(" where ").append(where);
				String delete = sql.toString();
				PreparedStatement st = null;
				st = conn.prepareStatement(delete);
				return st;
			}
		});
	}

	/**
	 * 根据dataId查询单条数据
	 * 
	 * @param tableName
	 * @param dataId
	 * @return
	 */
	public CmsData select(final DataTable table, Integer dataId) {
		StringBuilder sql = new StringBuilder("select * from ").append(table.getName()).append(" where ")
				.append(FieldCodes.ID).append("=").append(dataId);
		String select = sql.toString();
		final CmsData[] pdList = new CmsData[1];
		jdbcTemplate.query(select, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				try {
					pdList[0] = getResultPeopleData(rs, table.getId());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		return pdList[0];
	}

	/**
	 * 根据查询语句查询多条数据，分页查询
	 * 
	 * @param sql
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result<CmsData> select(final Integer tableId, String sql, Integer firstResult, Integer maxResults) {
		final Result<CmsData> result = new Result<CmsData>();
		StringBuilder sb = null;
		String select = null;
		int total = 0;
		final boolean[] useLimit = new boolean[1];
		useLimit[0] = false;
		if (firstResult == null) {
			total = 1;
		} else {
			useLimit[0] = true;
			sb = new StringBuilder("select count(*) ").append(sql.substring(sql.indexOf("from")));
			select = sb.toString();
			total = jdbcTemplate.queryForInt(select);
		}
		if (total > 0) {
			sb = new StringBuilder(sql);
			if (useLimit[0]) {
				sb.append(" limit ").append(firstResult).append(",").append(maxResults);
			}
			select = sb.toString();
			jdbcTemplate.query(select, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					CmsData pd = null;
					List<CmsData> list = new LinkedList<CmsData>();
					try {
						list.add(getResultPeopleData(rs, tableId));
						while (rs.next()) {
							pd = getResultPeopleData(rs, tableId);
							list.add(pd);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					result.setList(list);
					if (!useLimit[0]) {
						result.setTotalCount(list.size());
					}
				}
			});
		}
		return result;
	}

	/**
	 * 统计数据库条数
	 * 
	 * @param tableName
	 * @return
	 * @author Cheng
	 * @created 2013-1-8 下午2:40:52
	 */
	@SuppressWarnings("deprecation")
	public int count(String tableName) {
		int result = 0;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName);
		try {
			result = jdbcTemplate.queryForInt(sb.toString());
		} catch (Exception e) {
			logger.warn("统计数据库条数出错，表名：" + tableName, e);
		}
		return result;
	}

	/**
	 * 根据数据状态统计数据条数
	 * 
	 * @param tableName
	 * @param status
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int count(String tableName, EDataStatus status) {
		int result = 0;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName);
		try {
			result = jdbcTemplate.queryForInt(sb.toString());
		} catch (Exception e) {
			logger.warn("统计数据库条数出错，表名：" + tableName, e);
		}
		return result;
	}

	/**
	 * 今日新增数据库条数
	 * 
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int countByToday(String tableName) {
		int result = 0;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName)
				.append("  WHERE DATEDIFF(Create_Time,SYSDATE())=0");
		result = jdbcTemplate.queryForInt(sb.toString());
		return result;
	}

	/**
	 * 今日新增栏目稿件条数
	 * 
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int countByTodayAndStatus(String tableName, EDataStatus status) {
		int result = 0;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName)
				.append("  WHERE DATEDIFF(Create_Time,SYSDATE())=0  AND Data_Status=").append(status.ordinal());
		result = jdbcTemplate.queryForInt(sb.toString());
		return result;
	}

	/**
	 * 当日用户添加的文章数
	 * 
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int countByTodayUser(String tableName, Integer userId) {
		int result = 0;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName)
				.append("  WHERE DATEDIFF(Create_Time,SYSDATE())=0 ").append(" AND Creator_ID=").append(userId);
		result = jdbcTemplate.queryForInt(sb.toString());
		return result;
	}

	/**
	 * 根据查询语句查询所有数据，不分页
	 * 
	 * @param sql
	 * @return
	 */
	public Result<CmsData> select(int tableId, String sql) {
		return select(tableId, sql, null, null);
	}

	/**
	 * @param sql
	 * @return
	 */
	public CmsData selectSingle(final int tableId, String sql) {
		final CmsData[] pd = new CmsData[1];
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				try {
					pd[0] = getResultPeopleData(rs, tableId);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return pd[0];
	}

	/**
	 * 返回查询结果的第一列
	 * 
	 * @param sql
	 * @return
	 */
	public Object selectSingleField(String sql) {
		final Object[] obj = new Object[1];
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				rs.next();
				obj[0] = rs.getObject(1);
			}
		});
		return obj[0];
	}

	private String getFieldSql(DataField df) {
		StringBuilder sql = new StringBuilder();
		sql.append(df.getCode()).append(" ").append(df.getDataType().getMysqlDataType());
		if (EDataType.UUID.equals(df.getDataType())) {
			return sql.toString();
		}
		Integer i = df.getLeng();
		if (i != null && i > 0) {
			sql.append("(").append(i);
			i = df.getPrec();
			if (i != null && i > 0) {
				sql.append(",").append(i);
			}
			sql.append(")");
		}
		if (df.isNosg()) {
			sql.append(" unsigned");
		}
		if (df.isMand()) {
			sql.append(" NOT NULL");
		}
		if (EDataType.IntAutoIncrement.equals(df.getDataType())) {
			sql.append(" AUTO_INCREMENT");
		}
		return sql.toString();
	}

	private PreparedStatement getPreparedInsert(Connection conn, String tableName, DbData doc) throws SQLException {
		StringBuilder insert = new StringBuilder("insert into ").append(tableName).append("(");
		StringBuilder values = new StringBuilder("values(");
		int i = 0;
		for (Map.Entry<DataField, Object> en : doc.getEntryList()) {
			DataField df = en.getKey();
			if (FieldCodes.ID.equals(df.getCode())) {
				continue;
			}
			if (i > 0) {
				insert.append(",");
				values.append(",");
			}
			insert.append(df.getCode());
			values.append("?");
			i++;
		}
		insert.append(") ").append(values).append(");");
		String sql = insert.toString();

		return conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	}

	private void setPreparedInsert(PreparedStatement st, DbData doc) throws SQLException, UnsupportedEncodingException {
		int i = 1;
		StringBuilder sb = new StringBuilder(" set ");
		Object value = null;
		String fieldCode = null;
		for (Map.Entry<DataField, Object> en : doc.getEntryList()) {
			DataField df = en.getKey();
			fieldCode = df.getCode();
			value = en.getValue();
			if (FieldCodes.ID.equals(fieldCode)) {
				continue;
			} else if (value instanceof String
					&& (df.getDataType() == EDataType.Blob || df.getDataType() == EDataType.MediumBlob)) {
				sb.append(i).append("=<Blob>, ");

				st.setBlob(i, new ByteArrayInputStream(((String) value).getBytes("utf-8")));
				i++;
				continue;
			}

			sb.append(i).append("=").append(value).append(", ");
			st.setObject(i, value);
			i++;
		}
	}

	private String getStaticInsertSql(String tableName, DbData[] docs) {
		StringBuilder insert = new StringBuilder("insert into ").append(tableName).append("(");
		StringBuilder values = new StringBuilder("values(");
		int i = 0;
		if (docs.length == 1) {
			for (Map.Entry<DataField, Object> en : docs[0].getEntryList()) {
				DataField df = en.getKey();
				if (FieldCodes.ID.equals(df.getCode())) {
					continue;
				}
				if (i > 0) {
					insert.append(",");
					values.append(",");
				}
				insert.append(df.getCode());
				switchDataType(values, en);
				i++;
			}

		} else {
			for (Map.Entry<DataField, Object> en : docs[0].getEntryList()) {
				DataField df = en.getKey();
				if (FieldCodes.ID.equals(df.getCode())) {
					continue;
				}
				if (i > 0) {
					insert.append(",");
				}
				insert.append(df.getCode());
			}
			for (int j = 0; j < docs.length; j++) {
				if (j > 0) {
					values.append("),(");
				}
				for (Map.Entry<DataField, Object> en : docs[j].getEntryList()) {
					DataField df = en.getKey();
					if (FieldCodes.ID.equals(df.getCode())) {
						continue;
					}
					if (i > 0) {
						values.append(",");
					}

					switchDataType(values, en);

					i++;
				}
			}
		}

		insert.append(") ").append(values).append(");");
		return insert.toString();
	}

	private void switchDataType(StringBuilder values, Map.Entry<DataField, Object> en) {
		DataField df = en.getKey();
		Object value = en.getValue();
		if (value == null) {
			values.append("null");
		} else {
			switch (df.getDataType()) {
			case Char:
			case Varchar:
			case UUID:
				values.append("'").append(en.getValue()).append("'");
				break;
			case Date:
				values.append("'").append(DateTimeUtil.formatDate((Date) en.getValue())).append("'");
				break;
			case DateTime:
				values.append("'").append(DateTimeUtil.formatDateTime((Date) en.getValue())).append("'");
				break;
			case Time:
				values.append("'").append(DateTimeUtil.formatTime((Date) en.getValue())).append("'");
				break;
			case Short:
			case Int:
			case Long:
			case Float:
			case Double:
			case Numeric:
			case Bool:
			default:
				values.append(en.getValue());
				break;
			}
		}
	}

	private PreparedStatement getPreparedUpdate(Connection conn, String tableName, DbData doc) throws SQLException {
		StringBuilder update = new StringBuilder("update ").append(tableName).append(" set ");
		StringBuilder where = new StringBuilder();
		int i = 0;
		for (Map.Entry<DataField, Object> en : doc.getEntryList()) {
			DataField df = en.getKey();
			if (FieldCodes.ID.equals(df.getCode())) {
				if (where.length() > 0) {
					where.append(" and ");
				}
				where.append(df.getCode()).append("=?");
				if (FieldCodes.ID.equals(df.getCode())) {
					continue;
				}
			}

			if (i > 0) {
				update.append(",");
			}
			update.append(df.getCode()).append("=?");
			i++;
		}
		update.append(" where ").append(where).append(";");
		return conn.prepareStatement(update.toString());
	}

	private void setPreparedUpdate(PreparedStatement st, DbData doc) throws SQLException {
		int i = 1, n = 0;
		Object[] wheres = new Object[2];
		for (Map.Entry<DataField, Object> en : doc.getEntryList()) {
			DataField df = en.getKey();
			if (FieldCodes.ID.equals(df.getCode())) {
				wheres[n] = en.getValue();
				n++;
				continue;
			}

			st.setObject(i, en.getValue());
			i++;
		}
		st.setObject(i, wheres[0]);
	}

	private String getStaticUpdateSql(String tableName, DbData doc) {
		StringBuilder update = new StringBuilder("update ").append(tableName).append(" set ");
		StringBuilder where = new StringBuilder();
		int i = 0;
		for (Map.Entry<DataField, Object> en : doc.getEntryList()) {
			DataField df = en.getKey();
			if (FieldCodes.ID.equals(df.getCode())) {
				if (where.length() > 0) {
					where.append(" and ");
				}
				where.append(df.getCode()).append("=").append(en.getValue());
				continue;
			}
			if (i > 0) {
				update.append(",");
			}

			update.append(df.getCode()).append("=");
			switchDataType(update, en);
			i++;
		}

		update.append(" where ").append(where).append(";");
		return update.toString();
	}

	private CmsData getResultPeopleData(ResultSet rs, Integer tableId)
			throws SQLException, ParseException, UnsupportedEncodingException, IOException {
		ResultSetMetaData md = rs.getMetaData();
		CmsData pd = new CmsData();
		pd.setTableId(tableId);
		for (int i = 1; i <= md.getColumnCount(); i++) {
			String label = md.getColumnLabel(i);

			Object value = null;
			switch (md.getColumnType(i)) {
			case Types.INTEGER:
				value = rs.getInt(i);
				break;
			case Types.BIGINT:
				value = rs.getLong(i);
				break;
			case Types.TINYINT:
				value = rs.getShort(i);
				break;
			case Types.LONGVARBINARY:
			case Types.BLOB:
				value = rs.getBlob(i) == null ? null : StringUtil.toString(rs.getBlob(i));
				// if (null != value)
				// value = AESUtil.decrypt(value.toString(),
				// SystemConstant.KEY);
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				value = rs.getDate(i) == null ? null : DateTimeUtil.getDate(rs.getTimestamp(i).getTime());
				break;
			case Types.CHAR:
			case Types.VARCHAR:
				value = rs.getString(i);
				break;
			case Types.DOUBLE:
				value = rs.getDouble(i);
				break;
			case Types.FLOAT:
				value = rs.getFloat(i);
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				value = rs.getBoolean(i);
				break;
			}
			pd.put(label, value);
		}
		return pd;
	}
}

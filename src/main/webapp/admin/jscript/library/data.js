$(document).ready(function() {
    initPage();
    data_tb();
    InitSortTree();
    showForm(); //数据编辑页面
    // 初始化富文本编辑器
    if ($("#contentArea").length > 0) {
        UE.getEditor('contentArea');
    }
});

var thisPath = appPath + "/admin/userdb/";

function initPage() {
    $('#into_as_search').show();
    $('#into_as_search').attr("href", appPath + '/admin/advanceQuery/resrc_db/' + baseId + "/as");
}

var objTitle = [{
    "mData": "title",
    "fnRender": function(obj) {
        var sumImg = "";
        var attach = "";
        if (obj.aData.img) {
            sumImg = '<div class="sum_img_div"><img class="list_sum_img" src="' + obj.aData.img + '"/></div>';
        }
        if (obj.aData.attach) {
            attach = " <span class='icon icon-blue icon-attachment'></span>";
        }
        var result = '<h3><label class="checkbox inline mt0"><input type="checkbox" id="inlineCheckbox' + obj.aData.id + '" name="idStr' + obj.aData.id + '" value="' + obj.aData.id + '_' + obj.aData.tableId + '" style="opacity: 0;" >' + '</label>';
        if (obj.aData.status > 0 && obj.aData.status != 3) {
            result += obj.aData.title;
        } else {
            result += '<a class="data_title edit_pop_link" href="' + thisPath + 'data/edit/' + obj.aData.tableId + '/' + obj.aData.id + '" target="_blank">' + obj.aData.title + '</a>';
        }
        result += '<a  class="padmbt btn floatr none" href="' + thisPath + 'data/info?para=' + obj.aData.dataGroups + '"><i class="icon-eye-open" title = "文章预览"></i></a></h3><p class="summary clearfix" >' + sumImg + obj.aData.summary + attach + '</p>';
        return result;
    }
}];

/* 表格设置-数据列表 */
var baseId = $('#baseId').val();
var searchIdStr = $("#searchIdStr").val();
var fieldsStr = $("#fieldsStr").val();
var mSearch = $("#mSearch").val();

//初始化数据列表
function data_tb() {
    var titleList = fieldsStr.split(',');
    if (titleList != null && titleList != "") {
        $.each(titleList,
        function(index, field) {
            var temp = {
                "mData": field
            };
            objTitle.push(temp);
        });
    }
    dataTablesCom($('#dataTable'), "/admin/userdb/data/search/" + baseId, objTitle, null, datatalbesReadyData, false, false, true);
};

// 表格回调--数据列表
function datatalbesReadyData(oTableDataDb) {
    docReady();
    trHoverEdit();
    listSend(thisPath + "data/send", oTableDataDb); // 送审
    if ('NO_INSTANCE' == checkType) {
        $("#check_from_dsu").hide();
    }

    var chkUrl = appPath + "/admin/utask/batchChkForData";
    var objName;
    $('#check_success').click(function() {
        objName = "check_success";
        listBatchChk(objName, chkUrl, oTableDataDb); //批量送审
    });

    $('#check_failure').click(function() {
        objName = "check_failure";
        listBatchChk(objName, chkUrl, oTableDataDb); //批量送审
    });

    listDelete(thisPath + "data/delete", oTableDataDb); // 删除数据路径
    editPopWithDT(oTableDataDb); ////设置新增-修改文章弹出层
};

//稿件批量审核
/*批量审核通过*/
function listBatchChk(ObjId, sUrl, oTable) {
    $("batchChk").modal('hide');
    chceckTrPuls();
    if (ObjId == 'check_success') {
        sUrl = sUrl + "/Yes";
    } else if (ObjId == 'check_failure') {
        sUrl = sUrl + "/No";
    }
    if ($(".dataTables_wrapper").find("table input[type='checkbox']").length > 0) {
        var count = 0;
        var idsVal = new Array();
        $(".dataTables_wrapper").find("table input[type='checkbox']").each(function() {
            if ($(this).attr("checked") && $(this).val() != null && $(this).val().length > 0) {
                idsVal.push($(this).val());
                count++;
            }
        });
        var sData = idsVal.join(",");
        var spData = {
            name: "ids",
            value: sData
        };

        if (count > 0) {
            $("#batchChk").modal('show');
            $("#batchChk").find('.btn-primary').click(function() {
                $.ajax({
                    type: 'post',
                    contentType: "application/json",
                    url: sUrl,
                    data: JSON.stringify(spData),
                    success: function(resp) {
                        oTable.fnDraw();
                        setTimeout("isCheckboxStyle();", 300);
                        $('#batchChk').modal('hide');
                    },
                    error: function(data) {
                        $('#batchChk').modal('hide');
                        noty({
                            "text": "操作出错！",
                            "layout": "top",
                            "type": "information"
                        });
                    }
                });

            });
        }
        return false;
    }
    //});
}

/* data edit页面 */

// 判断匹配出现的表单
function showForm() {
    if ($("#Article_new_form").length > 0) {
        //		$("<div class='preShow alert'>加载中...</div>").insertBefore(
        //				"#Article_new_form");
        $("#Article_new_form").hide();
        var activeInput = $("#fieldsStr").val().split(",");
        for (var i = 0; i < $("#Article_new_form input").length; i++) {
            var flag = true;
            for (var j = 0; j < activeInput.length; j++) {
                if ($("#Article_new_form input:eq(" + i + ")").attr("id") == activeInput[j]) {
                    flag = false;
                    break;
                }

                if ($("#Article_new_form select:eq(" + i + ")").attr("id") == activeInput[j]) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                if ($("#Article_new_form input:eq(" + i + ")").hasClass("treeSel") || $("#Article_new_form input:eq(" + i + ")").hasClass("ios_toggle")) {
                    continue; // 跳过作为双input的情况
                }
                if ($("#Article_new_form input:eq(" + i + ")").attr("id") == "Imgs") {
                    $("#Article_new_form input:eq(" + i + ")").parents(".futu_imgs").addClass("beremove");
                } else if ($("#Article_new_form input:eq(" + i + ")").attr("id") == "Sort_Ids") {
                    $("#Article_new_form input:eq(" + i + ")").parents(".control-group").addClass("beremove");
                } else if ($("#Article_new_form input:eq(" + i + ")").attr("id") == "Data_Status") {
                    $("#Article_new_form input:eq(" + i + ")").parents(".control-group").addClass("beremove");
                } else if ($("#Article_new_form select:eq(" + i + ")").attr("id") == "Secret_Level") {
                    $("#Article_new_form select:eq(" + i + ")").parents(".control-group").addClass("beremove");
                } else {
                    $("#Article_new_form input:eq(" + i + ")").parents(".control-group").addClass("beremove");
                }

            }
        }
        // 判断类型为textarea的摘要是否需要显示
        var flagT = true;
        for (var j = 0; j < activeInput.length; j++) {
            if (activeInput[j] == "Summary") {
                flagT = false;
                break;
            }
        }
        if (flagT) {
            $("#Summary").parents(".control-group").addClass("beremove");
        }

        $(".beremove").remove();
        $("#Article_new_form").show();
        //		$(".preShow").remove();
        //设置ajax提交及关闭弹出层
        $("#Article_new_form").submit(function() {
            $(this).ajaxSubmit({
                success: function() {
                    parent.$.colorbox.close();
                }
            });
            return false;
        });
    }
}

//初始化数据编辑页面中的数据分类选择区域
function InitSortTree() {
    var url = appPath + "/admin/dataSort/" + $("#baseId").val() + "/tree";
    if ($("#sort_tree")[0]) {
        $.ajax({
            type: "POST",
            url: url,
            data: '[{"name":"id","value":""}]',
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                treeSleCom($("#sort_tree .treeNew"), data);
                setTimeout("$('.treeSelId').click()", 800);
            }
        });
    }
}
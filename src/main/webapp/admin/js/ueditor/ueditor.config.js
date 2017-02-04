(function() {
	var URL = window.UEDITOR_HOME_URL || appPath + "/admin/js/ueditor/"|| (function() {
				function PathStack() {
					this.documentURL = self.document.URL || self.location.href;
					this.separator = '/';
					this.separatorPattern = /\\|\//g;
					this.currentDir = './';
					this.currentDirPattern = /^[.]\/]/;
					this.path = this.documentURL;
					this.stack = [];
					this.push(this.documentURL);
				}
				PathStack.isParentPath = function(path) {
					return path === '..';
				};
				PathStack.hasProtocol = function(path) {
					return !!PathStack.getProtocol(path);
				};
				PathStack.getProtocol = function(path) {
					var protocol = /^[^:]*:\/*/.exec(path);
					return protocol ? protocol[0] : null;
				};
				PathStack.prototype = {
					push : function(path) {
						this.path = path;
						update.call(this);
						parse.call(this);
						return this;
					},
					getPath : function() {
						return this + "";
					},
					toString : function() {
						return this.protocol+ (this.stack.concat([ '' ])).join(this.separator);
					}
				};
				function update() {
					var protocol = PathStack.getProtocol(this.path || '');
					if (protocol) {
						this.protocol = protocol;
						this.localSeparator = /\\|\//.exec(this.path.replace(protocol, ''))[0];
						this.stack = [];
					} else {
						protocol = /\\|\//.exec(this.path);
						protocol && (this.localSeparator = protocol[0]);
					}
				}
				function parse() {
					var parsedStack = this.path.replace(this.currentDirPattern,'');
					if (PathStack.hasProtocol(this.path)) {
						parsedStack = parsedStack.replace(this.protocol, '');
					}
					parsedStack = parsedStack.split(this.localSeparator);
					parsedStack.length = parsedStack.length - 1;
					for ( var i = 0, tempPath, l = parsedStack.length, root = this.stack; i < l; i++) {
						tempPath = parsedStack[i];
						if (tempPath) {
							if (PathStack.isParentPath(tempPath)) {
								root.pop();
							} else {
								root.push(tempPath);
							}
						}
					}
				}
				var currentPath = document.getElementsByTagName('script');
				currentPath = currentPath[currentPath.length - 1].src;
				return new PathStack().push(currentPath) + "";
			})();
	window.UEDITOR_CONFIG = {
		UEDITOR_HOME_URL : URL,
		imageUrl : URL + "jsp/imageUp.jsp?baseId=" + $("#baseId").val()+ "&uuid=" + $("#uuid").val(),
		imagePath : appPath + "/",
		fileUrl : URL + "jsp/fileUp.jsp?baseId=" + $("#baseId").val()+ "&uuid=" + $("#uuid").val() + "&createTime="+ $("#createTime").val(),
		filePath : URL + "jsp/",
		catcherUrl : URL + "jsp/getRemoteImage.jsp?baseId="+ $("#baseId").val() + "&uuid=" + $("#uuid").val(),
		catcherPath : URL + "jsp/",
		imageManagerUrl : URL + "jsp/imageManager.jsp?baseId="+ $("#baseId").val() + "&uuid=" + $("#uuid").val(),
		//imageManagerPath : URL + "jsp/",
		imageManagerPath : "",
		imageOtherUrl : URL + "jsp/imageOther.jsp?baseId=" + $("#baseId").val()+ "&uuid=" + $("#uuid").val(),
		imageOtherPath : URL + "jsp/",
		snapscreenHost : location.hostname,
		snapscreenServerUrl : URL + "jsp/imageUp.jsp?baseId="+ $("#baseId").val() + "&uuid=" + $("#uuid").val(),
		snapscreenPath : URL + "jsp/",
		snapscreenServerPort : location.port,
		getMovieUrl : URL + "jsp/getMovie.jsp?baseId=" + $("#baseId").val()+ "&uuid=" + $("#uuid").val(),
		toolbars : [ [ 'source','|', 'undo', 'redo', '|','bold', 'italic', 'underline', 'fontborder', 'strikethrough','superscript', 'subscript',
		        'removeformat', 'formatmatch','autotypeset', 'pasteplain', '|', 'forecolor','backcolor', 'insertorderedlist', 'insertunorderedlist',
		        'selectall', 'cleardoc', '|', 'rowspacingtop','rowspacingbottom', 'lineheight', '|', 'customstyle','paragraph', 'fontfamily', 'fontsize', '|','directionalityltr',
		        'directionalityrtl', 'indent', '|','justifyleft', 'justifycenter', 'justifyright','justifyjustify', '|', 'touppercase', 'tolowercase', '|','link', 'unlink', '|', 'insertimage',
		        'insertvideo','background', '|', 'horizontal', 'date','time', 'spechars', '|', 'inserttable', 'deletetable','searchreplace' ] ],
		initialFrameWidth : "100%",
		initialFrameHeight : 420};
})();

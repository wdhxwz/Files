fileUpload : function(files, fileHandler) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i]; // 获取文件对象
                    var form = new FormData();
                    form.append("token", new Date().getTime());
                    form.append("file", file);
                    form.append("isMaxValue",this.batchForm.isMaxValue);
                    var xhr = new XMLHttpRequest();
                    xhr.open("post", "/point/adminBatchAdd.do", true);
                    xhr.onload = function (e) {
                        console.log("Upload finished", e.target.status, e.target.response);
                        var result = JSON.parse(e.target.response);
                        if (fileHandler) {
                            fileHandler(result);
                        }
                    };
                    xhr.send(form);
                }
            }
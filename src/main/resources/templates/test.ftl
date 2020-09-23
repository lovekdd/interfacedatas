<html>
<head>
    <title>接口测试</title>
    <link href="css/eui.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="eui-dialog-body" style="width:745px;height:565px">
    <div class="eui-layout-container eui-padding-top-10">
        <fieldset class="eui-area-h2">
            <legend>基础信息设置</legend>
        </fieldset>
        <div class="eui-input-inline">
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label eui-form-required">接口地址：</label>
                <div class="eui-input-block">
                    <input type="text" id="jkurl" class="eui-form-input" />
                    <button id="test" style="position:absolute;top:0;left:272px;" class="eui-btn eui-btn-m eui-btn-sub">测试</button>
                </div>
            </div>
        </div>
        <div class="eui-input-inline">
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label">用户名：</label>
                <div class="eui-input-block">
                    <input placeholder=" 需要登录认证则输入，否则留空" class="eui-form-input" id="jkid" />
                </div>
            </div>
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label">密码：</label>
                <div class="eui-input-block">
                    <input placeholder="需要登录认证则输入，否则留空" class="eui-form-input" id="jkpw" />
                </div>
            </div>
        </div>
        <div class="eui-input-inline">
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label">请求类型：</label>
                <div id="reqtype" class="eui-input-block">
                    <div class="eui-form-select" style="width:200px;">
                        <input type="text" class="eui-input" placeholder="" size="1" readonly="" title="Post" />
                        <i class="eui-icon eui-form-select-icon"></i>
                        <input type="hidden" class="eui-form-hidden" value="Post" />
                    </div>
                </div>
            </div>

        </div>
        <div class="eui-input-inline">
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label">原始编码：</label>
                <div id="originalcode" class="eui-input-block">
                    <div class="eui-form-select" style="width:200px;">
                        <input type="text" class="eui-input" placeholder="" size="1" readonly="" title="UTF-8" />
                        <i class="eui-icon eui-form-select-icon"></i>
                        <input type="hidden" class="eui-form-hidden" value="UTF-8" />
                    </div>
                </div>
            </div>
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label">目标编码：</label>
                <div id="targetcode" class="eui-input-block">
                    <div class="eui-form-select" style="width:200px;">
                        <input type="text" class="eui-input" placeholder="" size="1" readonly="" title="UTF-8" />
                        <i class="eui-icon eui-form-select-icon"></i>
                        <input type="hidden" class="eui-form-hidden" value="UTF-8" />
                    </div>
                </div>
            </div>
        </div>
        <div class="eui-input-inline">
            <div class="eui-form-item eui-input-inline">
                <label class="eui-form-label">格式：</label>
                <div id="format" class="eui-input-block">
                    <div class="eui-form-select" style="width:200px;">
                        <input type="text" class="eui-input" placeholder="" size="1" readonly="" title="JSON" />
                        <i class="eui-icon eui-form-select-icon"></i>
                        <input type="hidden" class="eui-form-hidden" value="JSON" />
                    </div>
                </div>
            </div>
        </div>
        <fieldset class="eui-area-h2">
            <legend>参数设置</legend>
        </fieldset>
        <div class="eui-margin-top-10" id="listoper">
            <div class="eui-coolbar-container eui-coolbar-btngray" _selectabletype_="false" style="user-select: none; padding-left: 0px;">
                <ul id="listoperband" class="eui-coolbar-group">
                    <li id="ECoolElement24" class="eui-coolbar-item"><span class="eui-coolbar-item-text">新增</span></li>
                    <li id="ECoolElement25" class="eui-coolbar-item"><span class="eui-coolbar-item-text">删除</span></li>
                </ul>
                <span class="eui-coolbar-more" title="显示列表" style="display: none;">0</span>
            </div>
        </div>
        <div class="eui-layout-row-3 eui-layout-row-last" id="paramarea" style="top:300px;">
            <div class="eui-datalist-container eui-datalist-mini" _selectabletype_="false" style="width: 100%; height: 100%; user-select: none;">
                <div class="eui-datalist-header">
                    <table style="position: absolute; left:1px;">
                        <tbody>
                        <tr>
                            <td style="width: 38px;"><i class="eui-icon eui-form-checkbox "></i><span style="margin: 0px 5px;"></span></td>
                            <td class="eui-datalist-edit" style="width: 135px;">参数名称</td>
                            <td class="eui-datalist-edit" style="width: 70px;">头部参数</td>
                            <td class="eui-datalist-edit" style="width: 200px;">参数描述</td>
                            <td class="eui-datalist-edit" style="width: 236px;">参数默认值</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="eui-datalist-body">
                    <table style="width: 1px;">
                        <colgroup>
                            <col style="width: 38px;" />
                            <col style="width: 135px;" />
                            <col style="width: 70px;" />
                            <col style="width: 200px;" />
                            <col style="width: 236px;" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="eui-datalist-data-col">
                                <div class="eui-datalist-edittext">
                                    <i class="eui-icon eui-form-checkbox"></i>
                                    <span style="margin: 0px 5px;"></span>
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="id">
                                    id
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="否">
                                    否
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="登录用户名">
                                    登录用户名
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="$fm_userid">
                                    $fm_userid
                                </div></td>
                        </tr>
                        <tr class="">
                            <td class="eui-datalist-data-col">
                                <div class="eui-datalist-edittext">
                                    <i class="eui-icon eui-form-checkbox"></i>
                                    <span style="margin: 0px 5px;"></span>
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="pw">
                                    pw
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="否">
                                    否
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="登录密码">
                                    登录密码
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="$fm_password">
                                    $fm_password
                                </div></td>
                        </tr>
                        <tr>
                            <td class="eui-datalist-data-col">
                                <div class="eui-datalist-edittext">
                                    <i class="eui-icon eui-form-checkbox"></i>
                                    <span style="margin: 0px 5px;"></span>
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="subjectId">
                                    subjectId
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="否">
                                    否
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title=""></div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="73351cbd9fe14bbfb99cd8b92842847b">
                                    73351cbd9fe14bbfb99cd8b92842847b
                                </div></td>
                        </tr>
                        <tr class="">
                            <td class="eui-datalist-data-col">
                                <div class="eui-datalist-edittext">
                                    <i class="eui-icon eui-form-checkbox"></i>
                                    <span style="margin: 0px 5px;"></span>
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="paramdata">
                                    paramdata
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="否">
                                    否
                                </div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title=""></div></td>
                            <td class="eui-datalist-data-col eui-datalist-edit-cell">
                                <div class="eui-datalist-edittext" title="{&quot;aaa&quot;:&quot;ELOG&quot;} ">
                                    {&quot;aaa&quot;:&quot;ELOG&quot;}
                                </div></td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="eui-datalist-tips eui-hide">
        <span>
         <div class="eui-nodate-container eui-nodate-mini">
          <span class="eui-nodate-imgbg"></span>
          <span class="eui-nodate-text">暂无数据</span>
         </div></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script>
    var button = document.getElementById("test");
    button.onclick = clickEvent;
    var map = new Map();
    map.set("id","001");
    map.set("name","test1");
    map.set("url","http://yun.zhuzhufanli.com/mini/select/?");
    map.set("params","[{'appid':'124986','outerid':'d5ZVkBM8VuQHS0Rx','isHeader':'false','taskname':'R200921_224980823','pageno':'1'}]");
    map.set("method","POST");
    map.set("dstEncoding","UTF-8");
    map.set("srcEncoding","UTF-8");
    function clickEvent(){
        debugger
        $.ajax({
            url : "/testinterfacedata",//点击测试请求地址
            type : "post",//请求方式
            data :  map,//数据,
            success : function(jdata) {
                //dosomething refreshdata;
                }
        });
    }
</script>
</html>

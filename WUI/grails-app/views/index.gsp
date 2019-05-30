<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>

    <style type="text/css">

    .ques {
        color: darkslateblue;
    }
    .switch {
        position: relative;
        display: inline-block;
        width: 130px;
        height: 50px;
    }

    .switch input {display:none;}

    .slider {
        position: absolute;
        cursor: pointer;
        overflow: hidden;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #f2f2f2;
        -webkit-transition: .4s;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        z-index: 2;
        content: "";
        height: 48px;
        width: 48px;
        left: 2px;
        bottom: 2px;
        background-color: darkslategrey;
        -webkit-box-shadow: 0 2px 5px rgba(0, 0, 0, 0.22);
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.22);
        -webkit-transition: .4s;
        transition: all 0.4s ease-in-out;
    }
    .slider:after {
        position: absolute;
        left: 0;
        z-index: 1;
        content: "YES";
        font-size: 22px;
        text-align: left !important;
        line-height: 44px;
        padding-left: 0;
        width: 130px;
        color: #fff;
        height: 50px;
        border-radius: 100px;
        background-color: #ff6418;
        -webkit-transform: translateX(-80px);
        -ms-transform: translateX(-80px);
        transform: translateX(-80px);
        transition: all 0.4s ease-in-out;
    }

    input:checked + .slider:after {
        -webkit-transform: translateX(0px);
        -ms-transform: translateX(0px);
        transform: translateX(0px);
        /*width: 235px;*/
        padding-left: 25px;
    }

    input:checked + .slider:before {
        background-color: #fff;
    }

    input:checked + .slider:before {
        -webkit-transform: translateX(80px);
        -ms-transform: translateX(80px);
        transform: translateX(80px);
    }

    /* Rounded sliders */
    .slider.round {
        border-radius: 100px;
    }

    .slider.round:before {
        border-radius: 50%;
    }
    .absolute-no {
        position: absolute;
        left: 0;
        color: darkslategrey;
        text-align: right !important;
        font-size: 22px;
        width: calc(100% - 25px);
        height: 50px;
        line-height: 44px;
        cursor: pointer;
    }
    </style>
</head>

<body>
<div class="text-center py-5">
    <div class="container">
        <div class="row my-5 justify-content-center">
            <div class="col-md-9">
                <h1>Data Extractor</h1>
            <!--<p class="lead text-muted">...</p>-->

                <g:uploadForm controller="main" action="upload">

                    <p>Select Images or PDF File</p>
                    <input type="file" class="btn btn-secondary m-2" name="img" multiple="multiple"/>
                    <br/>
                    <h4 class="ques">Process Descriptions?</h4>
                    <label class="switch">
                        <input type="checkbox" name="pd">
                        <span class="slider round"></span>
                        <span class="absolute-no">NO</span>
                    </label>

                    <p></p>
                    <g:submitButton name="upload" value="Upload"/>
                </g:uploadForm>
            </div>
        </div>
    </div>
</div>
<g:if test="${flash.hasUpload != null}">
    <div class="py-4 bg-light">
        <div class="container">
            <div class="row">
                <div class="col-md-6 p-3">
                    <div class="card box-shadow">
                        <ul class="list-group">
                            <li class="list-group-item"
                                contenteditable>Patient Name: ${flash.items?.data?."patient-names"?.getAt(0).toString().split("\n")[-1]}</li>
                            <li class="list-group-item"
                                contenteditable>Patient NRIC: ${flash.items?.data?.nric?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Bill No: ${flash.items?.data?.bill?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Bill Date/Time: ${flash.items?.data?."bill-date"?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>GL No: ${flash.items?.data?.gl?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Charge Type: ${flash.items?.data?.charge?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Admission Date: ${flash.items?.data?."admission-date"?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Discharge Date: ${flash.items?.data?."discharge-date"?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Total Gross: MYR${flash.items?.data?."total-gross"?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Total Discount: MYR${flash.items?.data?."total-discount"?.getAt(0)}</li>
                            <li class="list-group-item"
                                contenteditable>Total Payable: MYR${flash.items?.data?."total-net"?.getAt(0)}</li>
                        </ul>

                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">Descriptions</h5>
                                    <small>Detected</small>
                                </div>
                                <g:each in="${flash.items?.description}" var="org">
                                    <p class="mb-1">${org}</p>
                                </g:each>
                            </a>
                        </div>
                        <table class="display" id="example" style="width:100%">
                            <thead>
                            <tr>
                                <th scope="col">No</th>
                                <th scope="col">Description</th>
                                <th scope="col">Amount</th>
                                <th scope="col">Disc.</th>
                                <th scope="col">Tax</th>
                                <th scope="col">Total</th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${flash.items.data.items}" var="item" status="i">
                                <tr>
                                    <th scope="row">${i + 1}</th>
                                    <td contenteditable>${item[0]}</td>
                                    <td contenteditable>${item[1]}</td>
                                    <td contenteditable>${item[2]}</td>
                                    <td contenteditable>${item[3]}</td>
                                    <td contenteditable>${item[4]}</td>
                                </tr>
                            </g:each>
                            <tr>
                                <th scope="row"></th>
                                <td style="text-align: right">Subtotal</td>
                                <td contenteditable>${flash.sub}</td>
                                <td>${flash.discount}</td>
                                <td>${flash.tax}</td>
                                <td>${flash.total}</td>
                            </tr>
                            </tbody>
                        </table>

                    </div>
                </div>

                <div class="col-md-6 p-3">
                    <div class="card box-shadow">
                        <g:each in="${flash.image}" var="image">
                            <img class="card-img-top" src="${assetPath(src: "${image}")}">
                        </g:each>
                    </div>
                </div>

                <div class="col-md-6 p-3">
                    <div class="card box-shadow">
                    </div>
                </div>

            </div>
        </div>
    </div>
</g:if>
</body>
</html>

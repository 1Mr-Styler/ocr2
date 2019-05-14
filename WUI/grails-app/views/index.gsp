<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
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
                            <li class="list-group-item" contenteditable>Patient Name: ${flash.items?.data?."patient-names"?.getAt(0).toString().split("\n")[-1]}</li>
                            <li class="list-group-item" contenteditable>Patient NRIC: ${flash.items?.data?.nric?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Bill No: ${flash.items?.data?.bill?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Bill Date/Time: ${flash.items?.data?."bill-date"?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>GL No: ${flash.items?.data?.gl?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Charge Type: ${flash.items?.data?.charge?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Admission Date: ${flash.items?.data?."admission-date"?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Discharge Date: ${flash.items?.data?."discharge-date"?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Total Gross: MYR${flash.items?.data?."total-gross"?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Total Discount: MYR${flash.items?.data?."total-discount"?.getAt(0)}</li>
                            <li class="list-group-item" contenteditable>Total Payable: MYR${flash.items?.data?."total-net"?.getAt(0)}</li>
                        </ul>

                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">Organizations</h5>
                                    <small>Detected</small>
                                </div>
                                <g:each in="${flash.items?.data?.organization}" var="org">
                                    <p class="mb-1">${org}</p>
                                </g:each>
                            </a>
                        </div>
                        <table class="table table-dark">
                            <thead>
                            <tr>
                                <th scope="col">No</th>
                                <th scope="col">Description</th>
                                <th scope="col">Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${flash.items.data.items}" var="item" status="i">
                                <tr>
                                    <th scope="row">${i + 1}</th>
                                    <td contenteditable>${item[0]}</td>
                                    <td contenteditable>${item[1]}</td>
                                </tr>
                            </g:each>
                            <tr>
                                <th scope="row"></th>
                                <td style="text-align: right">Subtotal</td>
                                <td contenteditable>${flash.sub}</td>
                            </tr>
                            </tbody>
                        </table>


                    </div>
                </div>

                <div class="col-md-6 p-3">
                    <div class="card box-shadow" style="max-height: 680px; overflow-y: scroll">
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

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

                    <p>Select Imagess</p>
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
                            <li class="list-group-item">Patient Name: ${flash.items.data."patient-names"[0]}</li>
                            <li class="list-group-item">Patient NRIC: ${flash.items.data.nric?.getAt(0)}</li>
                            <li class="list-group-item">Admission Date: ${flash.items.data."admission-date"?.getAt(0)}</li>
                            <li class="list-group-item">Discharge Date: ${flash.items.data."discharge-date"?.getAt(0)}</li>
                            <li class="list-group-item">Total Gross: MYR${flash.items.data."total-gross"?.getAt(0)}</li>
                            <li class="list-group-item">Total Discount: MYR${flash.items.data."total-discount"?.getAt(0)}</li>
                            <li class="list-group-item">Total Payable: MYR${flash.items.data."total-net"?.getAt(0)}</li>
                        </ul>

                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">Organizations</h5>
                                    <small>Detected</small>
                                </div>
                                <g:each in="${flash.items.data.organization}" var="org">
                                    <p class="mb-1">${org}</p>
                                </g:each>
                            </a>
                        </div>

                    </div>
                </div>

                <div class="col-md-6 p-3">
                    <div class="card box-shadow">
                        <img class="card-img-top" src="${assetPath(src: "${flash.image}")}">
                    </div>
                </div>

                <div class="col-md-6 p-3">
                    <div class="card box-shadow">
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
                                    <td>${item[0]}</td>
                                    <td>${item[1]}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </div>
</g:if>
</body>
</html>

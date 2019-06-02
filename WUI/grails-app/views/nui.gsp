<!doctype html>
<html>
<head>
    <title>Verification</title>
    <meta name="layout" content="xtract">
</head>

<body>
<!-- Main Content -->
<div class="hk-pg-wrapper" style="background-color: #D2D8E6">
    <!-- Container -->
    <div class="container mt-xl-50 mt-sm-30 mt-15">
        <!-- Title -->
        <div class="hk-pg-header align-items-top">
            <div>
                <h2 class="hk-pg-title font-weight-600 mb-10">Verification</h2>
            </div>
        </div>
        <!-- /Title -->

        <!-- Row -->
        <div class="row">
            <div class="col-xl-12">
                <div class="hk-row">
                    <div class="col-lg-7">
                        <div class="card">
                            <div class="card-header card-header-action">
                                <h6></h6>

                                <div class="d-flex align-items-center card-action-wrap">
                                    <button class="btn btn-primary btn-block" type="submit">Export</button>
                                </div>
                            </div>

                            <div class="card card-refresh">
                                <div class="card-body">

                                    <form>
                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Patient Name :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="NG MOW KWANG"
                                                       value="${flash.name}"
                                                       name="patientname" type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Patient NRIC :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="830725-10-5153"
                                                       name="patientnric" value="${flash.items?.data?.nric?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Bill No :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="IPC-6400" name="billno"
                                                       value="${flash.items?.data?.bill?.getAt(0)}" type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Bill Date/Time :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="23/02/2019 12:22:00 PM"
                                                       name="date" value="${flash.items?.data?."bill-date"?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                GL No :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="PH/L5000/011909"
                                                       name="glno" value="${flash.items?.data?.gl?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Charge Type :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="InPatient"
                                                       name="chargetype" value="${flash.items?.data?.charge?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Admission Date :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="19/02/2019"
                                                       name="admission"
                                                       value="${flash.items?.data?."admission-date"?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Discharge Date :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="23/02/2019"
                                                       name="dischargedate"
                                                       value="${flash.items?.data?."discharge-date"?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Total Gross :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="MYR 16420.55"
                                                       name="totalgross"
                                                       value="${flash.items?.data?."total-gross"?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Total Discount :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="328.00" name="totaldis"
                                                       value="MYR${flash.items?.data?."total-discount"?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="col-md-3 form-group">
                                                Total Payable :
                                            </div>

                                            <div class="col-md-9 form-group">
                                                <input class="form-control" placeholder="MYR 16420.55"
                                                       name="totalpay"
                                                       value="MYR${flash.items?.data?."total-net"?.getAt(0)}"
                                                       type="text">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div class="card">
                                <div class="card-body pa-0">
                                    <div class="table-wrap">
                                        <div class="table-responsive">
                                            <table class="table table-hover mb-0">
                                                %{--                                                <thead style="color: #223F7D">--}%
                                                <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Description</th>
                                                    <th>Amount</th>
                                                    <th>Discount</th>
                                                    <th>Tax</th>
                                                    <th>Total</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <g:each in="${flash.items?.data?.items}" var="item" status="i">
                                                    <tr>
                                                        <td>${i + 1}</td>
                                                        <td contenteditable>${item[0]}</td>
                                                        <td contenteditable>${item[1]}</td>
                                                        <td contenteditable>${item[2]}</td>
                                                        <td contenteditable>${item[3]}</td>
                                                        <td contenteditable>${item[4]}</td>
                                                    </tr>
                                                </g:each>
                                                <tr>
                                                    <th scope="row" style="color: rgba(0, 0, 0, 0.0)">10000</th>
                                                    <td style="text-align: right">Subtotal</td>
                                                    <td contenteditable>${flash.sub}</td>
                                                    <td>${flash.discount}</td>
                                                    <td>${flash.tax}</td>
                                                    <td>${flash.total}</td>
                                                </tr>
                                                </tbody>
                                            </table>

                                            <div class="card-header card-header-action">
                                                <h6></h6>

                                                <div class="d-flex align-items-center card-action-wrap">
                                                    <button class="btn btn-primary btn-block" type="submit">Save
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="col-lg-5">
                        <g:each in="${flash.image}" var="image">
                            <div class="card overflow-hide border-0">
                                <div class="card-body pa-0">
                                    <div class="owl-carousel dots-on-item owl-theme owl_demo_1">
                                        <img src="${assetPath(src: "${image}")}">
                                    </div>
                                </div>
                            </div>
                        </g:each>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Row -->
    </div>
    <!-- /Container -->

</div>
<!-- /Main Content -->
</body>
</html>

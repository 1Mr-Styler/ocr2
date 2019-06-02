<!doctype html>
<html>
<head>
    <meta name="layout" content="xtract"/>
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

    .switch input {
        display: none;
    }

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

<!-- Main Content -->
<div class="hk-pg-wrapper"  style="background-color: #D2D8E6">
    <!-- Container -->
    <div class="container mt-xl-50 mt-sm-30 mt-15">
        <!-- Title -->
        <div class="hk-pg-header align-items-top">
            <div>
                <h2 class="hk-pg-title font-weight-600 mb-10">Dashboard</h2>
            </div>
        </div>
        <!-- /Title -->

        <!-- Row -->
        <div class="row">
            <div class="col-xl-12">
                <div class="hk-row">
                    <div class="card">

                        <div class="card card-refresh">
                            <div class="card-body">
                                <div class="col-lg-7" style="background-color: white">
                                    <g:uploadForm controller="main" action="upload">

                                        <p>Select Images or PDF File</p>
                                        <input type="file" class="btn btn-secondary m-2" name="img"
                                               multiple="multiple"/>
                                        <br/>
                                        <h4 class="ques">Process Descriptions?</h4>
                                        <label class="switch">
                                            <input type="checkbox" name="pd">
                                            <span class="slider round"></span>
                                            <span class="absolute-no">NO</span>
                                        </label>

                                        <p></p>

                                        <div class="d-flex align-items-center card-action-wrap col-md-8">
                                            <g:submitButton name="upload" value="Upload"
                                                            class="btn btn-primary btn-block"/>
                                        </div>
                                    </g:uploadForm>
                                </div>
                            </div>
                        </div>
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

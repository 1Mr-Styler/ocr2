<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- PAGE settings -->
    <title><g:layoutTitle default="Xtract"/></title>
    <link href="https://fonts.googleapis.com/css?family=Nunito:300,400,400i,600,700,800,900" rel="stylesheet">
    <asset:stylesheet href="lite-purple.min.css" id="gull-theme"/>
    <!-- Favicon -->
    <link rel="shortcut icon" href="${assetPath(src: "favicon.ico")}">
    <link rel="icon" href="${assetPath(src: "favicon.ico")}" type="image/x-icon">
    <g:layoutHead/>
    <!-- Toggles CSS -->
    <link href="${assetPath(src: "vendors/jquery-toggles/css/toggles.css")}" rel="stylesheet" type="text/css">
    <link href="${assetPath(src: "vendors/jquery-toggles/css/themes/toggles-light.css")}" rel="stylesheet"
          type="text/css">

    <!-- Toastr CSS -->
    <link href="${assetPath(src: "vendors/jquery-toast-plugin/dist/jquery.toast.min.css")}" rel="stylesheet"
          type="text/css">

    <!-- Custom CSS -->
    <link href="${assetPath(src: "dist/css/style.css")}" rel="stylesheet" type="text/css">

</head>

<body>

<!-- Preloader -->
<div class="preloader-it">
    <div class="loader-pendulums"></div>
</div>
<!-- /Preloader -->

<!-- HK Wrapper -->
<div class="hk-wrapper hk-vertical-nav">

    <!-- Top Navbar -->
    <nav class="navbar navbar-expand-xl navbar-light fixed-top hk-navbar">
        <a id="navbar_toggle_btn" class="navbar-toggle-btn nav-link-hover" href="javascript:void(0);"><span
                class="feather-icon"><i data-feather="menu"></i></span></a>
        <a class="navbar-brand" href="#">
            <img class="brand-img d-inline-block" src="${assetPath(src: "dist/img/logo-light.png")}" alt="brand"/>
        </a>
        <ul class="navbar-nav hk-navbar-content">

            <li class="nav-item dropdown dropdown-authentication">
                <a class="nav-link dropdown-toggle no-caret" href="#" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    <div class="media">
                        <div class="media-img-wrap">
                            <div class="avatar">
                                <img src="${assetPath(src: "dist/img/avatar12.jpg")}" alt="user" class="avatar-img rounded-circle">
                            </div>
                            <span class="badge badge-success badge-indicator"></span>
                        </div>

                        <div class="media-body">
                            <span>Jerry Sho<i class="zmdi zmdi-chevron-down"></i></span>
                        </div>
                    </div>
                </a>

                <div class="dropdown-menu dropdown-menu-right" data-dropdown-in="flipInX" data-dropdown-out="flipOutX">
                    <a class="dropdown-item" href="#"><i
                            class="dropdown-icon zmdi zmdi-account"></i><span>Profile</span></a>
                    <a class="dropdown-item" href="#"><i
                            class="dropdown-icon zmdi zmdi-card"></i><span>My balance</span></a>
                    <a class="dropdown-item" href="#"><i
                            class="dropdown-icon zmdi zmdi-email"></i><span>Inbox</span></a>
                    <a class="dropdown-item" href="#"><i
                            class="dropdown-icon zmdi zmdi-settings"></i><span>Settings</span></a>

                    <div class="dropdown-divider"></div>

                    <div class="sub-dropdown-menu show-on-hover">
                        <a href="#" class="dropdown-toggle dropdown-item no-caret"><i
                                class="zmdi zmdi-check text-success"></i>Online</a>

                        <div class="dropdown-menu open-left-side">
                            <a class="dropdown-item" href="#"><i
                                    class="dropdown-icon zmdi zmdi-check text-success"></i><span>Online</span></a>
                            <a class="dropdown-item" href="#"><i
                                    class="dropdown-icon zmdi zmdi-circle-o text-warning"></i><span>Busy</span></a>
                            <a class="dropdown-item" href="#"><i
                                    class="dropdown-icon zmdi zmdi-minus-circle-outline text-danger"></i><span>Offline</span>
                            </a>
                        </div>
                    </div>

                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#"><i class="dropdown-icon zmdi zmdi-power"></i><span>Log out</span>
                    </a>
                </div>
            </li>
        </ul>
    </nav>

    <!-- /Top Navbar -->

    <!-- Vertical Nav -->
    <nav class="hk-nav hk-nav-dark">
        <a href="javascript:void(0);" id="hk_nav_close" class="hk-nav-close"><span class="feather-icon"><i
                data-feather="x"></i></span></a>

        <div class="nicescroll-bar">
            <div class="navbar-nav-wrap">
                <ul class="navbar-nav flex-column">

                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="server"></i></span>
                            <span class="nav-link-text">Dashboard</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="pie-chart"></i></span>
                            <span class="nav-link-text">Processing</span>
                        </a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="eye"></i></span>
                            <span class="nav-link-text">Verification</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="send"></i></span>
                            <span class="nav-link-text">Xport</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="book"></i></span>
                            <span class="nav-link-text">Report</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="list"></i></span>
                            <span class="nav-link-text">Audit Logs</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span class="feather-icon"><i data-feather="settings"></i></span>
                            <span class="nav-link-text">Settings</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div id="hk_nav_backdrop" class="hk-nav-backdrop"></div>
    <!-- /Vertical Nav -->
    <g:layoutBody/>

</div>
<!-- /HK Wrapper -->

<!-- jQuery -->
<asset:javascript src="vendors/jquery/dist/jquery.min.js"/>

<!-- Bootstrap Core JavaScript -->
<asset:javascript src="vendors/popper.js/dist/umd/popper.min.js"/>
<asset:javascript src="vendors/bootstrap/dist/js/bootstrap.min.js"/>

<!-- Slimscroll JavaScript -->
<asset:javascript src="dist/js/jquery.slimscroll.js"/>

<!-- Fancy Dropdown JS -->
<asset:javascript src="dist/js/dropdown-bootstrap-extended.js"/>

<!-- FeatherIcons JavaScript -->
<asset:javascript src="dist/js/feather.min.js"/>

<!-- Toggles JavaScript -->
<asset:javascript src="vendors/jquery-toggles/toggles.min.js"/>
<asset:javascript src="dist/js/toggle-data.js"/>

<!-- Counter Animation JavaScript -->
<asset:javascript src="vendors/waypoints/lib/jquery.waypoints.min.js"/>
<asset:javascript src="vendors/jquery.counterup/jquery.counterup.min.js"/>

<!-- EChartJS JavaScript -->
<asset:javascript src="vendors/echarts/dist/echarts-en.min.js"/>

<!-- Owl JavaScript -->
<asset:javascript src="vendors/owl.carousel/dist/owl.carousel.min.js"/>

<!-- Toastr JS -->
<asset:javascript src="vendors/jquery-toast-plugin/dist/jquery.toast.min.js"/>

<!-- Init JavaScript -->
<asset:javascript src="dist/js/init.js"/>
<asset:javascript src="dist/js/dashboard4-data.js"/>
</body>
</html>

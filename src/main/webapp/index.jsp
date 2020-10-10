<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template title="home">
<jsp:attribute name="head">
	<link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/css?family=Muli:300,400,700,900"/>">
	<link rel="stylesheet" href="<c:url value="/fonts/icomoon/style.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/jquery-ui.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/owl.carousel.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/owl.theme.default.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/owl.theme.default.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/jquery.fancybox.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/bootstrap-datepicker.css"/>">
	<link rel="stylesheet" href="<c:url value="/fonts/flaticon/font/flaticon.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/aos.css"/>">
	<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</jsp:attribute>
<jsp:attribute name="bottomjs">
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script>
	$("input[name='reg_role']:radio").change(function() {
		$('#studonly').collapse($(this).val() == "student" ? "show" : "hide");});
	
	</script>
	<script src="<c:url value="/js/jquery-migrate-3.0.1.min.js"/>"></script>
	<script src="<c:url value="/js/jquery-ui.js"/>"></script>
	<script src="<c:url value="/js/popper.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/owl.carousel.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.stellar.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.countdown.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap-datepicker.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.easing.1.3.js"/>"></script>
	<script src="<c:url value="/js/aos.js"/>"></script>
	<script src="<c:url value="/js/jquery.fancybox.min.js"/>"></script>
	<script src="<c:url value="/js/jquery.sticky.js"/>"></script>
	<script src="<c:url value="/js/main.js"/>"></script>
</jsp:attribute>
    <jsp:body>
  <div class="site-wrap">

    <div class="site-mobile-menu site-navbar-target">
      <div class="site-mobile-menu-header">
        <div class="site-mobile-menu-close mt-3">
          <span class="icon-close2 js-menu-toggle"></span>
        </div>
      </div>
      <div class="site-mobile-menu-body"></div>
    </div>
    
    <header class="site-navbar py-4 js-sticky-header site-navbar-target" role="banner">
      
      <div class="container-fluid">
        <div class="d-flex align-items-center">
          <div class="site-logo"><a href="index.html">CS Unipi</a></div>

          <div class="mx-auto text-center">
            <nav class="site-navigation position-relative text-right" role="navigation">
              <ul class="site-menu main-menu js-clone-nav mx-auto d-none d-lg-block  m-0 p-0">
                <li><a href="#home-section" class="nav-link">Αρχική</a></li>
                <li><a href="#courses-section" class="nav-link">Μαθήματα</a></li>
                <li><a href="#programs-section" class="nav-link">Πορτφόλιο</a></li>
                <li><a href="#teachers-section" class="nav-link">Καθηγητές</a></li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
      
    </header>

    <div class="intro-section" id="home-section">
      
      <div class="slide-1" style="background-image: url('images/hero_1.jpg');" data-stellar-background-ratio="0.5">
        <div class="container">
          <div class="row align-items-center">
            <div class="col-12">
              <div class="row align-items-center">
                <div class="col-lg-6 mb-4">
                <div data-aos="fade-down" data-aos-delay="100"><t:alert /></div>
                  <h1  data-aos="fade-up" data-aos-delay="100">Τμήμα Πληροφορικής</h1>
                  <p class="mb-4"  data-aos="fade-up" data-aos-delay="200">Με την εγγραφή σας στην διαδικτυακή πλατφόρμα του Πανεπιστημίου, μένετε συνεχώς ενημερωμένοι για τυχόν αλλαγές στο πρόγραμμα διδασκαλίας, αναβολές μαθημάτων και ανακοινώσεις εργασιών.</p>
                  <p data-aos="fade-up" data-aos-delay="300"><button type="submit" class="btn btn-primary py-3 px-5 btn-pill" onclick="location.href='Login';">${not empty user ? 'Continue as ' += user.getUsername() : 'Εισοδος'}</button></p>
                </div>

                <div class="col-lg-5 ml-auto" data-aos="fade-up" data-aos-delay="500">
<!-------------Φορμα Sign UP----------------------------------------- --------- -->
					<form action="<c:url value="/SingUp"/>" method="post" class="form-box">
						<h3 class="h4 text-black mb-4">Κάντε Εγγραφή</h3>
						<div class="form-group">
							<input type="text" class="form-control" name="reg_username" id="reg_username" placeholder="Username" required>
						</div>
						<div class="form-group">
							<input type="email" class="form-control" name="reg_email" id="reg_email" placeholder="Email" required>
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="reg_password" id="reg_password" placeholder="Password" required>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="reg_role" id="studentradio" value="student" checked required>
							<label class="form-check-label" for="studentradio">Student</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="reg_role" id="professorradio" value="professor" required>
							<label class="form-check-label" for="professorradio">Professor</label>
						</div>
						<div class="form-group">
							<div class="form-row">
								<div class="col">
									<input type="text" class="form-control" name="reg_first_name" id="reg_first_name" placeholder="First name" required>
								</div>
								<div class="col">
									<input type="text" class="form-control" name="reg_last_name" id="reg_last_name" placeholder="Last name" required>
								</div>
							</div>
						</div>
						<div id="studonly" class="collapse show form-group">
							<input type="text" class="form-control" name="reg_am" id="reg_am" placeholder="AM">
						</div>
						<div class="form-group">
							<textarea class="form-control" name="reg_comment" id="reg_comment" rows="3" placeholder="Comment"></textarea>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-primary btn-pill" value="Sing Up">
						</div>
					</form>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>

    
    <div class="site-section courses-title" id="courses-section">
      <div class="container">
        <div class="row mb-5 justify-content-center">
          <div class="col-lg-7 text-center" data-aos="fade-up" data-aos-delay="">
            <h2 class="section-title">Μαθήματα</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="site-section courses-entry-wrap"  data-aos="fade-up" data-aos-delay="100">
      <div class="container">
        <div class="row">

          <div class="owl-carousel col-12 nonloop-block-14">

            <div class="course bg-white h-100 align-self-stretch">
              <figure class="m-0">
                <a href="course-single.html"><img src="images/img_1.jpg" alt="Image" class="img-fluid"></a>
              </figure>
              <div class="course-inner-text py-4 px-4">
               
                <div class="meta"><span class="icon-clock-o"></span>6 Μαθήματα / 8ο εξάμηνο</div>
                <h3><a href="#">Πληροφορικά Συστήματα</a></h3>
                <p>Java Web Programming: JSP/Servlets Web Services </p>
              </div>
              <div class="d-flex border-top stats">
                <div class="py-3 px-4"><span class="icon-users"></span> 319 φοιτητές</div>
                <div class="py-3 px-4 w-25 ml-auto border-left"><span class="icon-chat"></span> 2</div>
              </div>
            </div>

            <div class="course bg-white h-100 align-self-stretch">
              <figure class="m-0">
                <a href="course-single.html"><img src="images/img_2.jpg" alt="Image" class="img-fluid"></a>
              </figure>
              <div class="course-inner-text py-4 px-4">
                
                <div class="meta"><span class="icon-clock-o"></span>7 μαθήματα / 2ο εξάμηνο</div>
                <h3><a href="#">Δομές Δεδομένων</a></h3>
                <p>Αφηρημένοι Τύποι Δεδομένων. Λίστες. Στοίβες. Ουρές. Δέντρα. Γράφοι. Αλγόριθμοι Αναζήτησης. Αλγόριθμοι Ταξινόμησης.. </p>
              </div>
              <div class="d-flex border-top stats">
                <div class="py-3 px-4"><span class="icon-users"></span> 293 φοιτητές</div>
                <div class="py-3 px-4 w-25 ml-auto border-left"><span class="icon-chat"></span> 2</div>
              </div>
            </div>

            <div class="course bg-white h-100 align-self-stretch">
              <figure class="m-0">
                <a href="course-single.html"><img src="images/img_3.jpg" alt="Image" class="img-fluid"></a>
              </figure>
              <div class="course-inner-text py-4 px-4">
              
                <div class="meta"><span class="icon-clock-o"></span>6 μαθήματα / 8ο εξάμηνο</div>
                <h3><a href="#">Ασφάλεια Δικτύων</a></h3>
                <p>Ασφάλεια Δρομολόγησης, Σχεδιασμός συστημάτων Firewall, Ιδιωτικά Εικονικά Δίκτυα (VPN) </p>
              </div>
              <div class="d-flex border-top stats">
                <div class="py-3 px-4"><span class="icon-users"></span> 193 φοιτητές</div>
                <div class="py-3 px-4 w-25 ml-auto border-left"><span class="icon-chat"></span> 2</div>
              </div>
            </div>

            <div class="course bg-white h-100 align-self-stretch">
              <figure class="m-0">
                <a href="course-single.html"><img src="images/img_4.jpg" alt="Image" class="img-fluid"></a>
              </figure>
              <div class="course-inner-text py-4 px-4">
                
                <div class="meta"><span class="icon-clock-o"></span>7 μαθήματα / 6ο εξάμηνο</div>
                <h3><a href="#">Αλγόριθμοι</a></h3>
                <p>Αλγόριθμοι για προβλήματα ταξινόμησης, αναζήτησης, για προβλήματα γραφημάτων όπως διατρέξεις, συνεκτικές συνιστώσες, τοπολογική διάταξη, ελάχιστα γεννητικά δένδρα και συντομότερες διαδρομές. </p>
              </div>
              <div class="d-flex border-top stats">
                <div class="py-3 px-4"><span class="icon-users"></span> 367 φοιτητές</div>
                <div class="py-3 px-4 w-25 ml-auto border-left"><span class="icon-chat"></span> 2</div>
              </div>
            </div>

            <div class="course bg-white h-100 align-self-stretch">
              <figure class="m-0">
                <a href="course-single.html"><img src="images/img_5.jpg" alt="Image" class="img-fluid"></a>
              </figure>
              <div class="course-inner-text py-4 px-4">
               
                <div class="meta"><span class="icon-clock-o"></span>4 μαθήματα / 4ο εξάμηνο</div>
                <h3><a href="#">Θεωρία Πληροφοριών και Κωδίκων</a></h3>
                <p>Η Εντροπία ως μέτρο πληροφορίας, ∆ίαυλοι, Θεωρία κωδίκων Κώδικες, Κώδικες ανίχνευσης διόρθωσης σφάλματος. </p>
              </div>
              <div class="d-flex border-top stats">
                <div class="py-3 px-4"><span class="icon-users"></span> 133 φοιτητές</div>
                <div class="py-3 px-4 w-25 ml-auto border-left"><span class="icon-chat"></span> 2</div>
              </div>
            </div>

            <div class="course bg-white h-100 align-self-stretch">
              <figure class="m-0">
                <a href="course-single.html"><img src="images/img_6.jpg" alt="Image" class="img-fluid"></a>
              </figure>
              <div class="course-inner-text py-4 px-4">
             
                <div class="meta"><span class="icon-clock-o"></span>7 μαθήματα / 8ο εξάμηνο</div>
                <h3><a href="#">Σχεδίαση Υπολογιστικών Συστημάτων</a></h3>
                <p>Μοντελοποίηση ψηφιακών κυκλωμάτων. Εισαγωγή στις γλώσσες περιγραφής υλικού. Σχεδίαση ψηφιακών κυκλωμάτων με χρήση της γλώσσας VHDL. </p>
              </div>
              <div class="d-flex border-top stats">
                <div class="py-3 px-4"><span class="icon-users"></span> 93 φοιτητές</div>
                <div class="py-3 px-4 w-25 ml-auto border-left"><span class="icon-chat"></span> 2</div>
              </div>
            </div>

          </div>

        </div>
        <div class="row justify-content-center">
          <div class="col-7 text-center">
            <button class="customPrevBtn btn btn-primary m-1">Prev</button>
            <button class="customNextBtn btn btn-primary m-1">Next</button>
          </div>
        </div>
      </div>
    </div>

        <div class="row pt-5 mt-5 text-center">
          <div class="col-md-12">
            <div class="border-top pt-5">
            <p>
       
      </p>
            </div>
          </div>
          
        </div>
      </div>
      
    <!-- -------------------------------------site-wrap -->
	</jsp:body>
</t:template>

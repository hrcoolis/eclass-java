<%@ tag description="Alert Message" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" id="Course">
	<div class="intro-section single-cover" id="home-section">
		<div class="slide-1 " style="background-image: url('<c:url value="/images/img_2.jpg"/>'); background-position: 50% -25px;" data-stellar-background-ratio="0.5">
			<div class="container">
				<div class="row align-items-center">
					<div class="col-12">
						<div class="row justify-content-center align-items-center text-center">
							<div class="col-lg-6">
								<h1 data-aos="fade-up" data-aos-delay="0" class="aos-init aos-animate">${course.getTitle()}</h1>
								<p data-aos="fade-up" data-aos-delay="100" class="aos-init aos-animate">${course.getCode()} • TODO students • <a href="#" class="text-white">COUNT assignments</a></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="site-section">
		<div class="container">
				<jsp:doBody />
		</div>
	</div>
</div>
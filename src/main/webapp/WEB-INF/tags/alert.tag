<%@ tag description="Alert Message" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty alertMsg}">
<div class="alert alert-${not empty alertType ? alertType : 'danger'} alert-dismissible fade show" role="alert">
	${alertMsg}
	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
</c:if>
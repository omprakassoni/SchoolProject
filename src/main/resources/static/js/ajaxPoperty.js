$(function(){
	
	

	
	/**************************** Reloading Page on closing of Modal ***********************************************/
	
	$('#VideoModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#SubjectModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#TopicModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#QuizModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#ArticleModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#DocumentModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#LessonPlanModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#PhetModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#TestimonialModall').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	$('#EventModal').on('hidden.bs.modal', function () {
		location.reload();
	});
	
	
	
	
	
	
	
	/*****************************************END *********************************************************************/
	
	$('#subject').change(function(){
		
		$("#headerSubmit").prop('disabled', false);
		
		
	})
	// REST API CALL FUNCTIONALITY FOR TUTORIAL
	
	
		$('#topicTutorial').change(function(){
			
			$("#foss").prop('disabled', true);
			
			$.ajax({
				  	 type: "GET",
				
		       		 url: "https://spoken-tutorial.org/api/get_schoolfosslist/",
		       		 dataType: 'json',
		       		 cache: false,
		        	 timeout: 600000,
		       		 success: function (data){
		       			
			       	    var html = '';
			            var len = data.length;
			            html += '<option value="0">Select Foss</option>';
			            for (var i = 0; i < len; i++) {
			             html += '<option value="' + data[i].id + '">'
			               + data[i].foss
			               + '</option>';
			            }
			            html += '</option>';
		            
		        	$("#foss").prop('disabled', false);
		        	
		            $('#foss').html(html);
		           
		            $(".upload-submit").prop('disabled', false);
		         
		            
					},
					
					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
						
					}
					
					
				});
			
			
			
			
		})
		
		
		$('#foss').change(function(){
			
			var fossId=$(this).find(":selected").val();
			
			$("#fossLanguage").prop('disabled', true);
			
			var urlLanguage="https://spoken-tutorial.org/api/get_fosslanguage/"+fossId+"/";
			
			
			$.ajax({
				  	 type: "GET",
				
		       		 url: urlLanguage,
		       		 dataType: 'json',
		       		 cache: false,
		        	 timeout: 600000,
		       		 success: function (data){
		       			
			       	    var html = '';
			            var len = data.length;
			            html += '<option value="0">Select Language</option>';
			            for (var i = 0; i < len; i++) {
			             html += '<option value="' + data[i].id + '">'
			               + data[i].name
			               + '</option>';
			            }
			            html += '</option>';
		            
		        	$("#fossLanguage").prop('disabled', false);
		        	
		            $('#fossLanguage').html(html);
		         
		            
					},
					
					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
						
					}
					
					
				});
			
			
			
			
		})
		
		$('#fossLanguage').change(function(){
			
			var fossLanguageId=$(this).find(":selected").val();
			var fossId = $("#foss").val();
			
			$("#fossTutorial").prop('disabled', true);
			
			var urlFossAndLanguage="https://spoken-tutorial.org/api/get_tutorials/"+fossId+"/"+fossLanguageId+"/";
			
			
			$.ajax({
				  	 type: "GET",
				
		       		 url: urlFossAndLanguage,
		       		 dataType: 'json',
		       		 cache: false,
		        	 timeout: 600000,
		       		 success: function (data){
		       			
			       	    var html = '';
			            var len = data.length;
			            html += '<option value="0">Select Tutorial</option>';
			            for (var i = 0; i < len; i++) {
			             html += '<option value="' + data[i].id + '">'
			               + data[i].tutorial_name
			               + '</option>';
			            }
			            html += '</option>';
		            
		        	$("#fossTutorial").prop('disabled', false);
		        	
		            $('#fossTutorial').html(html);
		         
		            
					},
					
					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
						
					}
					
					
				});
			
			
			
			
		})
	
	
	// JQUERY AJAX CALL TO TAKE CONTACT DATA FROM USER SIDE ----------------------------------------
	
	
		$('#contactForm').click(function(){
			var name=$('#name').val();
			var email=$('#email').val();
			var desc=$('#message').val();
			if(name.length>0 && (email.endsWith(".com") ||email.endsWith(".in")) && desc.length>0){
				
				var json={
						"name":name,
						"message":desc,
						"email":email,
				};
				var jsdata= JSON.stringify(json);
				
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				$.ajax({
				  	type: "POST",
		        	contentType: "application/json",
		       		 url: "/addContactForm",
		       		 data: JSON.stringify(json),
		       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
		       		 },
		       		 dataType: 'json',
		       		 cache: false,
		        	 timeout: 600000,
		        	
		       		 success: function (data){
		       			 
		       			 
		       			$('#statusOnContactForm').css({"display": "none"}); 
						  
						 $('#statusOnContactFormAfterAjaxCallSucess').css({"display": "none"});
						 $('#statusOnContactFormAfterAjaxCallFailure').css({"display": "none"});
						
						 if(data[0]==="Success"){
							 $('#statusOnContactFormAfterAjaxCallSucess').css({"display": "block"});
							 setTimeout(function() {
						            $('#statusOnContactFormAfterAjaxCallSucess').fadeOut(1000)}, 4000);
						 }else {
							 $('#statusOnContactFormAfterAjaxCallFailure').css({"display": "block"});
							 setTimeout(function() {
						            $('#statusOnContactFormAfterAjaxCallFailure').fadeOut(1000)}, 4000);
						 }
						 
						 $("#name").prop('value', "");
						 $("#email").prop('value', "");
						 $("#message").prop('value', "");
						 
						 setTimeout(function() {
					            $('#Failure').fadeOut(1000)}, 4000);
						 
		       	    
		            
					},
					
					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
					}
					
					
				});
				
				
			}else{
				$('#statusOnContactForm').css({"display": "block"});
			}
			
		});
		
		
		/*-------------------------------UPDATE TESTIMONIAL FROM VIEW TESTIMONIAL LIST ---------------------------------------------*/
		
		$(".detailTestimonial").click(function(){
				var testi_id=$(this).attr('value');
				
				var json={
					"testimonialId":testi_id
				};
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/loadByTestimonialID",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       			 
	       			
	       			$('#testimonialDesc').attr('placeholder',data["description"]);
	       			$('#testimonialName').attr('placeholder',data["name"]);
	       			$('#testimonialOrg').attr('placeholder',data["organization"]);
	       		
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
			});
				



				$('#testiId').prop('value',testi_id);
				$('#TestimonialModal').modal('show');
			})
			
			
			/*-------------------------------UPDATE EVENT FROM VIEW EVENT LIST ---------------------------------------------*/
			
			
			
			$(".detailEvent").click(function(){
				var event_id=$(this).attr('value');
				
				var json={
					"eventId":event_id
				};
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/loadByEventID",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       			 
	       			
	       			$('#eventDesc').attr('placeholder',data["description"]);
	       			$('#eventHead').attr('placeholder',data["headline"]);
	       			$('#eventdate').attr('placeholder',data["dateToHappen"]);
	       		
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
			});
				



				$('#eventId').prop('value',event_id);
				$('#EventModal').modal('show');
			})
			
			
	/*----------------------------- UPDATE OF TESTIMONIAL------------------------------------------------------------------------------*/
			
			
			$("#updateTestimonial").click(function(){
				var testi_id=$('#testiId').val();
				var name=$('#testimonialName').val();
				var org=$('#testimonialOrg').val();
				var desc=$('#testimonialDesc').val();
				
				
				var json={
					"testimonialId":testi_id,
					"name":name,
					"organization":org,
					"description":desc,
					
				};
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/updateTestimonial",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       			 
	       			
	       			 
	       			 $('#Success').css({"display": "none"}); 
	    			 $('#Failure').css({"display": "none"});
	    			
	    			 if(data[0]==="Success"){
	    				 $('#Success').css({"display": "block"});
	    			 }else if(data[0]==="failure"){
	    				 $('#Failure').css({"display": "block"});
	    			 }
	       			 
	       			
	       		
	       		
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
			});
				


			})
			
			
			/*-------------------------------UPDATE OF EVENT ---------------------------------------------*/
			
			
			$("#updateEvent").click(function(){
				var event_id=$('#eventId').val();
				var desc=$('#eventDesc').val();
				var head=$('#eventHead').val();
				var date=$('#eventdate').val();
				
				var json={
					"eventId":event_id,
					"description":desc,
					"dateToHappen":date,
					"headline":head
					
				};
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/updateEvent",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       			 
	       			 $('#Success').css({"display": "none"}); 
	    			 $('#Failure').css({"display": "none"});
	    			
	    			 if(data[0]==="Success"){
	    				 $('#Success').css({"display": "block"});
	    			 }else if(data[0]==="failure"){
	    				 $('#Failure').css({"display": "block"});
	    			 }
	       			 
	       			
	       		
	       		
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
			});
				



				$('#eventId').prop('value',event_id);
				$('#EventModal').modal('show');
			})
			
		
	/************************************************************************************************************************************/	
		
		
		/*************************************************END**************************************************************************/
	
	
	
	/* -----------------------------------------------END----------------------------------------------------------------------*/
	
	
	//------------------------------JQUERY AJAX FOR CHANGING OF CLASS SUBJECT AND TIPIC--------------------------------------------------------------
	
	//*******************************************CONCEPTS-MAP*************************************************************************
			
			$('#classSelectedConcept').change(function(){
			
			var classname=$(this).find(":selected").val();
			var json={
					"className":classname,
			};
			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			

		
			$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/loadByClassName",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       	    var html = '';
	            var len = data.length;
	            html += '<option value="0">Select Subject</option>';
	            for (var i = 0; i < len; i++) {
	             html += '<option value="' + data[i] + '">'
	               + data[i]
	               + '</option>';
	            }
	            html += '</option>';
	            
	            $("#subjectConcept").prop('disabled', false);
	            $('#subjectConcept').html(html);
	            
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
				
			});
		
			
		});
			
		
		
		$('#subjectConcept').change(function(){
			
			var subject=$(this).find(":selected").val();
			var classname = $("#classSelectedConcept").val();
			var json={
					"subject":subject,
					"className":classname,
					
			};
			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
		
			$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/loadByClassnameAndSubject",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       	    var html = '';
	            var len = data.length;
	            html += '<option value="0">Select Topic</option>';
	            for (var i = 0; i < len; i++) {
	             html += '<option value="' + data[i] + '">'
	               + data[i]
	               + '</option>';
	            }
	            html += '</option>';
	            
	           
	            $('#topicConcept').html(html);
	            $("#topicConcept").prop('disabled', false);
		      
	            
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
				
			});
			
		 
		  
		});
		
	
		
		
		$('#topicConcept').change(function(){
			
			
		  	$("#descriptionConceptMap").prop('disabled', false);
		  
		  	
			$("#headlineConceptMap").prop('disabled', false);
			
			$("#conceptMapImage").prop('disabled', false);
			
			
		})
			
	//******************************************* PHETS ******************************************************************************/
	$('#classSelectedPhet').change(function(){
			
			var classname=$(this).find(":selected").val();
			var json={
					"className":classname,
			};
			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			

		
			$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/loadByClassName",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       	    var html = '';
	            var len = data.length;
	            html += '<option value="0">Select Subject</option>';
	            for (var i = 0; i < len; i++) {
	             html += '<option value="' + data[i] + '">'
	               + data[i]
	               + '</option>';
	            }
	            html += '</option>';
	            
	            $("#subjectPhet").prop('disabled', false);
	            $('#subjectPhet').html(html);
	            
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
				
			});
		
			
		});
			
		
		
		$('#subjectPhet').change(function(){
			
			var subject=$(this).find(":selected").val();
			var classname = $("#classSelectedPhet").val();
			var json={
					"subject":subject,
					"className":classname,
					
			};
			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
		
			$.ajax({
			  	type: "POST",
	        	contentType: "application/json",
	       		 url: "/loadByClassnameAndSubject",
	       		 data: JSON.stringify(json),
	       		 beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		 },
	       		 dataType: 'json',
	       		 cache: false,
	        	 timeout: 600000,
	       		 success: function (data){
	       	    var html = '';
	            var len = data.length;
	            html += '<option value="0">Select Topic</option>';
	            for (var i = 0; i < len; i++) {
	             html += '<option value="' + data[i] + '">'
	               + data[i]
	               + '</option>';
	            }
	            html += '</option>';
	            
	           
	            $('#topicPhet').html(html);
	            $("#topicPhet").prop('disabled', false);
		      
	            
				},
				
				error : function(err){
					console.log("not working. ERROR: "+JSON.stringify(err));
				}
				
				
			});
			
		 
		  
		});
		
	
		
		
		$('#topicPhet').change(function(){
			
			
		  	$("#descriptionPhet").prop('disabled', false);
		  
		  	
			$("#sourcePhet").prop('disabled', false);
			
			$("#Embedphet").prop('disabled', false);
			
			
		})
		
	/*******************************************************END*****************************************************************
	 */	
		
	
		$('#classSelectedArticle').change(function(){
  					
  					var classname=$(this).find(":selected").val();
  					var json={
  							"className":classname,
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassName",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Subject</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            $("#subjectArticle").prop('disabled', false);
  			            $('#subjectArticle').html(html);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  				
  					
  				});
  				
  			/* -----------------------------------------------------End of code ----------------- -------------------------------------*/	
  				
  		    /* ------------------------------------Start to fetch Topic name based on Class And Subject Selected -------------------------------------  */	
  				
  				
  				$('#subjectArticle').change(function(){
  					
  					var subject=$(this).find(":selected").val();
  					var classname = $("#classSelectedArticle").val();
  					var json={
  							"subject":subject,
  							"className":classname,
  							
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassnameAndSubject",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Topic</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			           
  			            $('#topicArticle').html(html);
  			            $("#topicArticle").prop('disabled', false);
	  			      
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  					
  				 
  				  
  				});
  				
  				/*---------------------------------- End of fetching topic name based on classname and subject------------------------------ */
  				
  				
  				$('#topicArticle').change(function(){
  					
  					
  				  	$("#descriptionArticle").prop('disabled', false);
  	
  				  	$("#urlArticle").prop('disabled', false);
	  				$("#sourceArticle").prop('disabled', false);
	  		
  				});
  				
  	/******************************************************* ARTCILE END********************************************************************/			
  				
  				
  				$('#classSelectedLesson').change(function(){

  					
  					var classname=$(this).find(":selected").val();
  					var json={
  							"className":classname,
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassName",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Subject</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            $("#subjectLesson").prop('disabled', false);
  			            $('#subjectLesson').html(html);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  				
  					
  				});
  				
  			/* -----------------------------------------------------End of code ----------------- -------------------------------------*/	
  				
  		    /* ------------------------------------Start to fetch Topic name based on Class And Subject Selected -------------------------------------  */	
  				
  				
  				$('#subjectLesson').change(function(){
  					
  					var subject=$(this).find(":selected").val();
  					var classname = $("#classSelectedLesson").val();
  					var json={
  							"subject":subject,
  							"className":classname,
  							
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassnameAndSubject",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Topic</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			         
  			            $('#topicLesson').html(html);
  			            $("#topicLesson").prop('disabled', false);
	  			    
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  					
  				 
  				  
  				});
  				
  				/*---------------------------------- End of fetching topic name based on classname and subject------------------------------ */
  				
  				
  				$('#topicLesson').change(function(){
  					
  					

	  				$("#lesson").prop('disabled', false);
	  			
  					
  				})
  				
  				
  		/************************************* END OF LESSON PLAN************************************************************/
  				
  				
  				$('#classSelectedDocument').change(function(){
  					
  					var classname=$(this).find(":selected").val();
  					var json={
  							"className":classname,
  					};
  					
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  		
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassName",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Subject</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            
  			            $("#subjectDocument").prop('disabled', false);
  			            $('#subjectDocument').html(html);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  				
  					
  				});
  				
  			/* -----------------------------------------------------End of code ----------------- -------------------------------------*/	
  				
  		    /* ------------------------------------Start to fetch Topic name based on Class And Subject Selected -------------------------------------  */	
  				
  				
  				$('#subjectDocument').change(function(){
  					
  					var subject=$(this).find(":selected").val();
  					var classname = $("#classSelectedDocument").val();
  					var json={
  							"subject":subject,
  							"className":classname,
  							
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassnameAndSubject",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Topic</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			           
  			            $('#topicDocument').html(html);
  			            $("#topicDocument").prop('disabled', false);
	  			   
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  					
  				 
  				  
  				});
  				
  				/*---------------------------------- End of fetching topic name based on classname and subject------------------------------ */
  				
  				
  				$('#topicDocument').change(function(){
  					
  					
  				  	$("#descriptionDocument").prop('disabled', false);
  		
  				  	$("#UrlDocument").prop('disabled', false);
	  				$("#sourceDocument").prop('disabled', false);
	  		
  					
  				})
  				
  	/************************************************ END OF DOCUMENT ***************************************************/
  				
  				
  				
  				$('#classSelectedVideo').change(function(){
  					
  					var classname=$(this).find(":selected").val();
  					var json={
  							"className":classname,
  					};
  					
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  		
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassName",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Subject</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            $("#subjectVideo").prop('disabled', false);
  			            $('#subjectVideo').html(html);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  				
  					
  				});
  				
  			/* -----------------------------------------------------End of code ----------------- -------------------------------------*/	
  				
  		    /* ------------------------------------Start to fetch Topic name based on Class And Subject Selected -------------------------------------  */	
  				
  				
  				$('#subjectVideo').change(function(){
  					
  					var subject=$(this).find(":selected").val();
  					var classname = $("#classSelectedVideo").val();
  					var json={
  							"subject":subject,
  							"className":classname,
  							
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassnameAndSubject",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Topic</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			          
  			            $('#topicVideo').html(html);
  			            $("#topicVideo").prop('disabled', false);
	  			    
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  					
  				 
  				  
  				});
  				
  				/*---------------------------------- End of fetching topic name based on classname and subject------------------------------ */
  				
  				
  				$('#topicVideo').change(function(){
  					
  					
  				  	$("#descriptionVideo").prop('disabled', false);
  				  
  				  	$("#urlVideo").prop('disabled', false);
	  				$("#sourceVideo").prop('disabled', false);
	  			
  				})
  				
  	/*************************************** END OF VIDEO********************************************/
  				
  				
  				$('#classSelectedQuiz').change(function(){
  					
  					var classname=$(this).find(":selected").val();
  					var json={
  							"className":classname,
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassName",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Subject</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            $("#subjectQuiz").prop('disabled', false);
  			            $('#subjectQuiz').html(html);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  				
  					
  				});
  				
  			/* -----------------------------------------------------End of code ----------------- -------------------------------------*/	
  				
  		    /* ------------------------------------Start to fetch Topic name based on Class And Subject Selected -------------------------------------  */	
  				
  				
  				$('#subjectQuiz').change(function(){
  					
  					var subject=$(this).find(":selected").val();
  					var classname = $("#classSelectedQuiz").val();
  					var json={
  							"subject":subject,
  							"className":classname,
  							
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassnameAndSubject",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Topic</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			           
  			            $('#topicQuiz').html(html);
  			            $("#topicQuiz").prop('disabled', false);
	  			     
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  					
  				 
  				  
  				});
  				
  				/*---------------------------------- End of fetching topic name based on classname and subject------------------------------ */
  				
  				
  				$('#topicQuiz').change(function(){
  					
  					$(".upload-submit").prop('disabled', false);
  					$("#QuestionQuiz").prop('disabled', false);
  					$("#remarksQuiz").prop('disabled', false);
  				  
  					
	  				$("#AnswerQuiz").prop('disabled', false);
	  			
  					
  				})
	
	
	
	//----------------------------------------------------VALIDATING EMAIL AGANIST DUPLICATE ENTRY-------------------------------------
	
			$('#validateEmailL').change(function(){
				
				var data=[];
				data[0]=$(this).val();
				
				
				$('#availableL').css({"display": "none"});
				$('#notAvailableL').css({"display": "none"});
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				if(validateEmail(data)){
				
				$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/validateEmail",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 
			       			 if(data[0]=="TRUE"){
			       				 
			       				$('#notAvailableL').css({"display": "block"}); 
			       				
			       			 }else{
			       				 
			       				$('#availableL').css({"display": "block"});
			       			 }
					     
			            
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
						
					});
				}else{
					
					$('#notAvailableL').css({"display": "block"}); 
				}
				
				
				
			})
			
			
			$('#validateEmailP').change(function(){
				
				var data=[];
				data[0]=$(this).val();
				
				
				$('#availableP').css({"display": "none"});
				$('#notAvailableP').css({"display": "none"});
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				if(validateEmail(data)){
				
				$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/validateEmail",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 
			       			 if(data[0]=="TRUE"){
			       				 
			       				$('#notAvailableP').css({"display": "block"}); 
			       				
			       			 }else{
			       				 
			       				$('#availableP').css({"display": "block"});
			       			 }
					     
			            
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
						
					});
				}else{
					
					$('#notAvailableP').css({"display": "block"});
					
				}
				
				
				
			})
			
			$('#validateEmailT').change(function(){
				
				var data=[];
				data[0]=$(this).val();
				
				
				$('#availableT').css({"display": "none"});
				$('#notAvailableT').css({"display": "none"});
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				
				if(validateEmail(data)){
				
				$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/validateEmail",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 
			       			 if(data[0]=="TRUE"){
			       				 
			       				$('#notAvailableT').css({"display": "block"}); 
			       				
			       			 }else{
			       				 
			       				$('#availableT').css({"display": "block"});
			       			 }
					     
			            
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
						
					});
				
				}else{
					
					$('#notAvailableT').css({"display": "block"}); 
				}
				
				
				
			})
			
	
	
	
	
	
	
	
	
	
	
	//------------------------------------------------------------------END-----------------------------------------------------------
	
  		// ------------------------------------------------ADDING TOPIC TO VIEW-----------------------------------------------------
  				
  				$('#classSelected,#inputClass').change(function(){
  					
  					var classname=$(this).find(":selected").val();
  					var json={
  							"className":classname,
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  		
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassName",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Subject</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            $("#subject").prop('disabled', false);
  			            $('#subject').html(html);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  				
  					
  				});
  				
  			/* -----------------------------------------------------End of code ----------------- -------------------------------------*/	
  				
  		    /* ------------------------------------Start to fetch Topic name based on Class And Subject Selected -------------------------------------  */	
  				
  				
  				$('#subject').change(function(){
  					
  					var subject=$(this).find(":selected").val();
  					var classname = $("#classSelected").val();
  					var json={
  							"subject":subject,
  							"className":classname,
  							
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  				
  					$.ajax({
  					  	type: "POST",
  			        	contentType: "application/json",
  			       		 url: "/loadByClassnameAndSubject",
  			       		 data: JSON.stringify(json),
  			       		 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
  			       		 },
  			       		 dataType: 'json',
  			       		 cache: false,
  			        	 timeout: 600000,
  			       		 success: function (data){
  			       	    var html = '';
  			            var len = data.length;
  			            html += '<option value="0">Select Topic</option>';
  			            for (var i = 0; i < len; i++) {
  			             html += '<option value="' + data[i] + '">'
  			               + data[i]
  			               + '</option>';
  			            }
  			            html += '</option>';
  			            
  			            $("#subject").prop('disabled', false);
  			            $('#topic').html(html);
  			            $('#topicTutorial').html(html);
  			            
  			            $("#topic").prop('disabled', false);
  			            $("#topicTutorial").prop('disabled', false);
	  			      	$("#addtopic").prop('disabled', false);
	  				  	$("#descriptionTopic").prop('disabled', false);
	  					$("#posterTopic").prop('disabled', false);
	  					$("#upload-topic").prop('disabled', false);
  			            
  						},
  						
  						error : function(err){
  							console.log("not working. ERROR: "+JSON.stringify(err));
  						}
  						
  						
  					});
  					
  				 
  				  
  				});
  				
  				/*---------------------------------- End of fetching topic name based on classname and subject------------------------------ */
  				
  				
  				$('#topic').change(function(){
  					
  					
  				  	$("#description").prop('disabled', false);
  				  	$("#poster").prop('disabled', false);
  					$("#Question").prop('disabled', false);
  				  	$("#Answer").prop('disabled', false);
  				  	$("#url").prop('disabled', false);
	  				$("#source").prop('disabled', false);
	  				$("#lesson").prop('disabled', false);
	  				$("#phet").prop('disabled', false);
	  				$("#Answer").prop('disabled', false);
	  				$("#Answer").prop('disabled', false);
	  				$('.upload-submit').prop('disabled',false)
	  				$("#descriptionConceptMap").prop('disabled', false);
	  				$("#headlineConceptMap").prop('disabled', false);
	  				$("#conceptMapImage").prop('disabled', false);
	  				
	  				
  					
  				})
  				
  				
  				/* -----------------------------------radio button call to enable DELETE button -----------------------------*/
  				
  				$("input[name='radiocall']").change(function(){
  					
  					$("#enableUser").prop('disabled', true);
  					$("#disableUser").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"id":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidity",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableUser").prop('disabled', false);
			       			 }else{
			       				$("#enableUser").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  				
  				
  				$("input[name='radioTopic']").change(function(){
  					
  					$("#enableTopic").prop('disabled', true);
  					$("#disableTopic").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"topicId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityTopic",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableTopic").prop('disabled', false);
			       			 }else{
			       				$("#enableTopic").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioPhet']").change(function(){
  					
  					$("#enablePhet").prop('disabled', true);
  					$("#disablePhet").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"phetId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityPhet",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disablePhet").prop('disabled', false);
			       			 }else{
			       				$("#enablePhet").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioLesson']").change(function(){
  					
  					$("#enableLesson").prop('disabled', true);
  					$("#disableLesson").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"lessonPlanId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityLesson",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableLesson").prop('disabled', false);
			       			 }else{
			       				$("#enableLesson").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioDocument']").change(function(){
  					
  					$("#enableDocument").prop('disabled', true);
  					$("#disableDocument").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"documentId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityDocument",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableDocument").prop('disabled', false);
			       			 }else{
			       				$("#enableDocument").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioArticle']").change(function(){
  					
  					$("#enableArticle").prop('disabled', true);
  					$("#disableArticle").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"articleId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityArticle",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableArticle").prop('disabled', false);
			       			 }else{
			       				$("#enableArticle").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioQuiz']").change(function(){
  					
  					$("#enableQuiz").prop('disabled', true);
  					$("#disableQuiz").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"quizQuestionId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityQuiz",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableQuiz").prop('disabled', false);
			       			 }else{
			       				$("#enableQuiz").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioVideo']").change(function(){
  					
  					$("#enableVideo").prop('disabled', true);
  					$("#disableVideo").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"videoId":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityVideo",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableVideo").prop('disabled', false);
			       			 }else{
			       				$("#enableVideo").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  					$("input[name='radioConcept']").change(function(){
  					
  					$("#enableConcept").prop('disabled', true);
  					$("#disableConcept").prop('disabled', true);
  					
  					var user_id=$(this).val();
  					var json={
  							"concepMapid":user_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityConcept",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableConcept").prop('disabled', false);
			       			 }else{
			       				$("#enableConcept").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  				
  				$("input[name='radioTutorial']").change(function(){
  					
  					$("#disableTutorial").prop('disabled', true);
  					$("#enableTutorial").prop('disabled', true);
  					
  					var tutorial_id=$(this).val();
  					var json={
  							"tutorialId":tutorial_id
  					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByValidityTutorial",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 if(data[0]==1){
			       				$("#disableTutorial").prop('disabled', false);
			       			 }else{
			       				$("#enableTutorial").prop('disabled', false);
			       			 }
			       			
			       			
			       			
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  						
  				})
  				
  				/*-----------------------------------------END--------------------------------------------------------------------------*/
  				
  				/*----------------------------------------------JQUERY TO ENABLE BUTTON ON RADIO CALL--------------------------------------------*/
  					$("input[name='selectionRadio']").change(function(){
  					
  						$("#deleteSubject").prop('disabled', false);
  						$("#deletePhet").prop('disabled', false);
  						$("#deleteVideo").prop('disabled', false);
  						$("#deleteArticle").prop('disabled', false);
  						$("#deleteDocument").prop('disabled', false);
  						$("#deleteLesson").prop('disabled', false);
  						$("#deleteTopic").prop('disabled', false);
  						$("#deleteQuiz").prop('disabled', false);
  						$("#deleteConcept").prop('disabled', false);
  					})
  				
  				
  				
  				
  				/*--------------------------------------------------END-------------------------------------------------------*/
  				
  				$(".detailUser").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"id":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByUser",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 
			       		
			       			 
			       			$('#FirstName').attr('value',data.fname);
		  					$('#LastName').attr('value',data.lname);
		  					$('#Email').attr('value',data.email);
		  					$('#Sex').attr('value',data.sex);
		  					$('#Dob').attr('value',data.dateOfBirth);
		  					$('#SchoolN').attr('value',data.schoolName);
		  					$('#SchoolA').attr('value',data.schoolAddress);
		  					$('#Pincode').attr('value',data.pincode);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
//  					$('#FirstName').attr('value','Om Prakash');
//  					$('#LastName').attr('value','Om Prakash');
//  					$('#Email').attr('value','Om Prakash');
//  					$('#Sex').attr('value','Om Prakash');
//  					$('#Role').attr('value','Om Prakash');
//  					$('#Dob').attr('value','Om Prakash');
//  					$('#SchoolN').attr('value','Om Prakash');
//  					$('#SchoolA').attr('value','Om Prakash');
//  					$('#Pincode').attr('value','Om Prakash');
  					
  					$('#LearnerModal').modal('show');
  				})
  				
  				
  		/*-----------------------------------------------START OF ZOOMING UP THE PHOTO---------------------------------------------------------	*/	
  				
  				$(".pop").on("click", function() {
  				   $('#imagepreview').attr('src', $(this).find('img').attr('src'));
  				   $('#imagepreview').attr('src', $(this).attr('src'));// here asign the image to the modal when the user click the enlarge link
  				   $('#imagemodal').modal('show'); // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
  				   
  				});
  				
  		/*------------------------------------------------------------END------------------------------------------------------------------------*/	
  				
  		/*****************************************************START OF ZOOMING UP THE DOCUMENT*****************************************************/		
  				
  				$(".pdfclass").on("click", function() {
   				   $('#pdfpreview').attr('src', $(this).find('img').attr('src')); // here asign the image to the modal when the user click the enlarge link
   				   $('#pdfmodal').modal('show'); // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
   				});
  		
  		/**************************************************************END*****************************************************************************/
  				
  			//-------------------------------------  Subject Details -----------------------------------------------------*/
  				
  				$(".detailSubject").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"subId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByClass",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 var html = '';
			       			 var len = data.length;
			       			 
			       			 for(var i=0;i<len;i++){
			       				 
			       				 html += '<input class="form-check-input checkboxSubject" type="checkbox" name="SelectedClasses" id="checkboxClass" value="';
			       				 html += data[i] +'"/>';
			       				 html += '<label class="form-check-label" for="defaultCheck1">'+data[i]+'</label><br/>' ;
			       				
			       			 }
			       			$('#modalClass').html(html);
			       		
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadBySubject",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 var html = '';
			       			 var len = data.length;
			       			 
			       			
			       			$('#SubjectName').attr('value',data["subName"]);
						  
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
							
						}
						
					});
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadBySubjectClass",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			 var html = '';
			       			 var len = data.length;
			       			
			       			 for(var i=0;i<len;i++){
			       				 
			       				
			       				$('#modalClass [type=checkbox]').each(function() {
			       				   if($(this).val()==data[i]){
			       					$(this).prop('disabled', true);
			       					}
			       				});
			       				
			       			 }
			       			
			       			
						  
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
							
						}
						
					});
  					
  					
  					
  				//	$('#FirstName').attr('value','Om Prakash');
  			
  				
  					$('#updateSubject').prop('value',user_id);
  					$('#SubjectModal').modal('show');
  				})
  				
  				

  			// ---------------------------------END ------------------------------------------------------------	
  				
  			// ----------------------------------Update Subject from Modal------------------------------------	
  				
  				
  				$("#updateSubject").click(function(){
  					
  					var data=[];
  					var i=0;
  				
  					
  					$('#modalClass [type=checkbox]').each(function() {
	       				   if($(this).is(':checked')){
	       					data[i++]=$(this).val();
	       					}
	       				});
  					
  					data[i]=$(this).val();
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  			
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/updateSubject",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#Success').css({"display": "none"}); 
			    			 $('#Failure').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#Success').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#Failure').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
				
  					
  					
  				})
  				
  			// ---------------------------END OF UPDATE SUBJECT CALL ----------------------------------	
  				
  				
  				
  				
  // 		------------------------------- START OF DETAILS OF TOPIC ---------------------------------------
  				
  				$(".detailTopic").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"topicId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByTopic",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#topicName').attr('value',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByTopicDesc",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#topicDesc').attr('placeholder',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  	
  					$('#updateTopic').prop('value',user_id);
  					$('#TopicId').prop('value',user_id);
  					$('#TopicModal').modal('show');
  				});
  			
  		//-----------------------------------------------------END-----------------------------------------------------------------	
  				
  		// --------------------------------- START OF UPDATE PART OF TOPIC------------------------------------------------------------
  				

  					$('#posterQ').change(function(){
  					 	$("#updateTopic").prop('disabled', false);
  					});
  				
  					
  				
  					$('#updateTopic').click(function(){
  						
  						 event.preventDefault();
  						 

  						fire_ajax_submit_Topic(); 
  					});
  				
  			
  					
  					
  			
  		// ----------------------------------------------------END-------------------------------------------------------------------
  				
  		// ---------------------------------------------START OF CALLING MODAL FROM QUIZ MODULE-----------------------------------------------
  				
  				
  				$(".detailQuiz").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"quizQuestionId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByQuizQuestionID",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#remark').attr('value',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
 
  	
  					$('#quizId').prop('value',user_id);
  					$('#QuizModal').modal('show');
  				})
  				
  			/* -------------------------------------------------END---------------------------------------------------------------------- */
  				
  			/* ---------------------------------------START OF UPDATING QUIZ DATA--------------------------------------------------*/
  				
  					$('#question').change(function(){
  						
  						if ($('#question').get(0).files.length > 0 && $('#answer').get(0).files.length > 0) {
  							$("#updateQuiz").prop('disabled', false);
  						}
  					 	
  					})
  				
  					
  					$('#answer').change(function(){
  						
  						if ($('#question').get(0).files.length > 0 && $('#answer').get(0).files.length > 0) {
  							$("#updateQuiz").prop('disabled', false);
  						}
  					})
  				
  					$('#updateQuiz').click(function(){
  						
  						 event.preventDefault();
  						

  						 fire_ajax_submit_Quiz(); 
  					});
  				
  					$('#updateQuizOnUser').click(function(){
						
						 event.preventDefault();
						

						 fire_ajax_submit_QuizOnUser(); 
					});
  				
  				
  			/*--------------------------------------------------END--------------------------------------------------------------------------------*/
  				
  				
  			// ------------------------------------------------START TO CALL MODAL FROM VIDEO MODULE ---------------------------------------			$(".detailvideo").click(function(){
  					
  					$(".detailVideo").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"videoId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByVideoID",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#videoDesc').attr('placeholder',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByVideoIDSource",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#videosource').attr('value',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
 
  	
  					$('#videoId').prop('value',user_id);
  					$('#VideoModal').modal('show');
  				})
  				
  				
  			/* -------------------------------------------------------------END-----------------------------------------------------------*/
  				
  			/* --------------------------------------------START OF UPDATING VIDEO--------------------------------------------------------*/
  				

  				
  					$('#updateVideo').click(function(){
  						
  						 event.preventDefault();
  						

  						 fire_ajax_submit_Video(); 
  					});
  					
  					$('#updateVideoOnUser').click(function(){
  						
 						 event.preventDefault();
 						

 						 fire_ajax_submit_VideoOnUser(); 
 					});
  			/*----------------------------------------------------------------END-------------------------------------------------------------*/
  			 /*----------------------------------------- CALLING MODAL FOR CONCEPT-MAPS -----------------------------------------*/
  					
  					$(".detailConcept").click(function(){
  	  					var user_id=$(this).attr('value');
  	  					
  	  					var json={
  								"concepMapid":user_id
  						};
  	  					
  	  					var token = $("meta[name='_csrf']").attr("content");
  	  					var header = $("meta[name='_csrf_header']").attr("content");
  	  					
  	  					$.ajax({
  						  	type: "POST",
  				        	contentType: "application/json",
  				       		 url: "/loadByConceptID",
  				       		 data: JSON.stringify(json),
  				       		 beforeSend: function(xhr) {
  		                         xhr.setRequestHeader(header, token);
  				       		 },
  				       		 dataType: 'json',
  				       		 cache: false,
  				        	 timeout: 600000,
  				       		 success: function (data){
  				       			
  				       			$('#conceptDesc').attr('placeholder',data[0]);
  				       			$('#conceptHeadline').attr('value',data[1]);
  				       		
  							},
  							
  							error : function(err){
  								console.log("not working. ERROR: "+JSON.stringify(err));
  							}
  							
  						});
  	  	
  	  	
  	  					$('#conceptId').prop('value',user_id);
  	  					$('#ConceptMapModal').modal('show');
  	  				})
  	  				
  	  				
  	  			/* -------------------------------------------------------------END-----------------------------------------------------------*/
  	  				
  	  			/* --------------------------------------------START OF UPDATING CONCEPT--------------------------------------------------------*/
  	  					$('#conceptImage').change(function(){
  					
  	  						$('#updateConcept').prop('disabled',false);
  	  						$('#updateConceptOnUser').prop('disabled',false);
  	  					})
  	  				

  	  				
  	  					$('#updateConcept').click(function(){
  	  						
  	  						 event.preventDefault();
  	  						

  	  						 fire_ajax_submit_Concept(); 
  	  					});
  	  					
  	  					$('#updateConceptOnUser').click(function(){
  	  						
  	 						 event.preventDefault();
  	 						

  	 						 fire_ajax_submit_ConceptOnUser(); 
  	 					});
  	  			/*----------------------------------------------------------------END-------------------------------------------------------------*/
  				
  				
  			// ---------------------------------------------------START TO CALL MODAL FROM ARTICLE MOPDULE----------------------------------
  	
  					$(".detailArticle").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"articleId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByArtcileID",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#articlesource').attr('value',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByArtcileIDDesc",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#articleDesc').attr('placeholder',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
 
  					
  					$('#artcileId').prop('value',user_id);
  					$('#ArticleModal').modal('show');
  				})
  				
  			/*---------------------------------------------------------------END-------------------------------------------------------------*/	
  			
  				
  			/* --------------------------------------------START OF UPDATING ARTICLE--------------------------------------------------------*/
  				
  					$('#updateArticle').click(function(){
  						
  						 event.preventDefault();
  						

  						 fire_ajax_submit_Article(); 
  					});
  					
  					$('#updateArticleOnUser').click(function(){
  						
 						 event.preventDefault();
 						

 						 fire_ajax_submit_ArticleOnUser(); 
 					});
  					
  					
  			/*----------------------------------------------------------------END-------------------------------------------------------------*/
  				
  					
  					
  			// ------------------------------------------------------STRAT TO CALL MODAL FROM DOCUMENT MODULE---------------------------------		
  	
  					$(".detailDocument").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"documentId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByDocumentID",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#documentsource').attr('value',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByDocumentIDDesc",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#documentDesc').attr('placeholder',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
 
  	
  					$('#documentId').prop('value',user_id);
  					$('#DocumentModal').modal('show');
  				})
  						
  			/* ---------------------------------------------------------END--------------------------------------------------------------*/
  				
  				
  			/* --------------------------------------------START OF UPDATING DOCUMENT--------------------------------------------------------*/
  				$('#document').change(function(){
  					
  					$('#updateDocument').prop('disabled',false);
  				})
  					
  				
  				
  				
  				$('#updateDocument').click(function(){
  						
  						 event.preventDefault();
  						

  						 fire_ajax_submit_Document(); 
  					});
  					
  					
  				$('#updateDocumentOnUser').click(function(){
  						
 						 event.preventDefault();
 						

 						 fire_ajax_submit_DocumentOnUser(); 
 					});
  			/*----------------------------------------------------------------END-------------------------------------------------------------*/
  			
  			/*------------------------------------------------ START TO CALL MODAL FROM LESSON PLAN--------------------------------------------*/
  					$(".detailLesson").click(function(){
  	  					var user_id=$(this).attr('value');
  	  					
  	  					
  	  					$('#lessonId').prop('value',user_id);
  	  					$('#LessonPlanModal').modal('show');
  	  				})
  				
  				
  			/*------------------------------------------------------------END-------------------------------------------------------------------*
  				
  		    /*-----------------------------------------------START OF UPDATE LESSON PLAN FROM MODAL---------------------------------------------*/
  	  				
  	  					$('#updateLesson').click(function(){
  						
  						 event.preventDefault();
  						

  						 fire_ajax_submit_Lesson(); 
  					});
  					
  						$('#updateLessonOnUser').click(function(){
  						
 						 event.preventDefault();
 						

 						 fire_ajax_submit_LessonOnUser(); 
 					});
 	  				
  	  				
  	  				
  	  		/*----------------------------------------------------------END-----------------------------------------------------------------------*/
  					
  					
  			// --------------------------------------------------START TO CALL MODAL FROM PHETS MODULE-------------------------------------
  				
  					$(".detailPhets").click(function(){
  					var user_id=$(this).attr('value');
  					
  					var json={
							"phetId":user_id
					};
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByphetID",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#phetsource').attr('value',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByphetIDDesc",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			$('#phetDesc').attr('placeholder',data[0]);
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
 
  	
  					$('#phetId').prop('value',user_id);
  					$('#PhetModal').modal('show');
  				})
  				
  		/*-----------------------------------------------END-----------------------------------------------------------------*/
  				
  		/* --------------------------------------------START OF UPDATING PHETS--------------------------------------------------------*/
  				
					$('#updatePhet').click(function(){
						
						 event.preventDefault();
						

						 fire_ajax_submit_Phet(); 
					});
  					
  					
  					$('#updatePhetOnUser').click(function(){
						
						 event.preventDefault();
						

						 fire_ajax_submit_PhetOnUser(); 
					});
  					
		/*----------------------------------------------------------------END-------------------------------------------------------------*/
			
  		/*****************************START OF COMMENT SECTION*******************************************************************************/
  					
  					
  					
  					
  			/************************************************VIDEO APPROVE/REJECT SECTION COMMENT SECTION***********************************************/
  					
  					
  					
  				$('.commentVideo').click(function(){
  					
  					
  					var Id=$(this).attr('value');
  					
  					var json={
  						"videoId":Id	
  					};
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
					  	
			        	contentType: "application/json",
			       		 url: "/loadByVideoComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> ';
					       			 //html+= data[i].dateReceived;
					       			 html+= '</div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong><span class="float-right" style="color:green">';
					       			 html+=data[i].dateReceived;
					       			 html+='</span></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="reply(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					       	
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/>';
							       				// html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong><span class="float-right" style="color:green">';
							       				 html+=data[i].dateReceived;
							       				 html+='</span></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataVideo').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					$('#videoId').prop('value',Id);
  					$('#VideoCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  				
  				$('#videoComment').change(function(){
  					
  					
  					$('.commentVideoModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentVideoModal,.commentVideoModalReply').click(function(){
  					
  					var data;
	
  					if($(this).prop('name')=="reply"){
  						
  						if($('#videoFrom').val()=="admin"){
  						
  						 data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyCommentVideo').val(),
  								"reply":true,
  								"admin":true
  						};
  						 
  						}else{
  							
  							 data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyCommentVideo').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						};
  							
  							
  						}
  						
  					}else{
  						
  						if($('#videoFrom').val()=="admin"){
  						
  							data={
  								"id":$('#videoId').val(),
  								"comment":$('#videoComment').val(),
  								"reply":false,
  								"admin":true
  							};
  						}else{
  							
  							data={
  	  								"id":$('#videoId').val(),
  	  								"comment":$('#videoComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  							};
  							
  							
  						}
  						
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnVideo",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessVideoComment').css({"display": "none"}); 
			    			 $('#FailureVideoComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessVideoComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailureVideoComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  					
  					
  				
  				/************************************************ARTICLE APPROVE/REJECT SECTION COMMENT SECTION***********************************************/
  					
  					
  				
  				$('.commentArticle').click(function(){
  					
  					var  Id=$(this).attr('value');
  					
  					var json={
  						"articleId":Id	
  					};
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
					  	
			        	contentType: "application/json",
			       		 url: "/loadByArticleComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
					       			 html+= data[i].dateReceived;
					       			 html+= '</p></div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="replyArticle(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					       	
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
							       				 html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataArticle').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					
  					
  					$('#articleId').prop('value',Id);
  					$('#ArticleCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  				
  				$('#articleComment').change(function(){
  					
  					
  					$('.commentArticleModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentArticleModal,.commentArticleModalReply').click(function(){
  					
  					var data;
  					
  					if($(this).prop('name')=="reply"){
  						
  						if($('#articleFrom').val()=="admin"){
  						
  						 data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyCommentArticle').val(),
  								"reply":true,
  								"admin":true
  						};
  						}else{
  							
  							 data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyCommentArticle').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						};
  							
  							
  						}
  						
  					}else{
  						
  						if($('#articleFrom').val()=="admin"){
  						
  							data={
  								"id":$('#articleId').val(),
  								"comment":$('#articleComment').val(),
  								"reply":false,
  								"admin":true
  							};
  							
  						}else{
  							
  							data={
  	  								"id":$('#articleId').val(),
  	  								"comment":$('#articleComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  							};
  							
  						}
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnArticle",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessArticleComment').css({"display": "none"}); 
			    			 $('#FailureArticleComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessArticleComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailureArticleComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  					
  			
  				/************************************************DOCUMENT APPROVE/REJECT SECTION COMMENT SECTION***********************************************/
  					
  				
  				$('.commentDocument').click(function(){
  					
  					var Id=$(this).attr('value');
  					
  					var json={
  						"documentId":Id	
  					};
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
					  	
			        	contentType: "application/json",
			       		 url: "/loadByDocumentComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
					       			 html+= data[i].dateReceived;
					       			 html+= '</p></div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="replyDocument(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					       		
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
							       				 html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataDocument').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					
  					
  					
  					$('#documentId').prop('value',Id);
  					$('#DocumentCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  				
  				$('#documentComment').change(function(){
  					
  					
  					$('.commentDocumentModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentDocumentModal,.commentDocumentModalReply').click(function(){
  					
 
  					var data;
  					
  					if($(this).prop('name')=="reply"){
  						
  						if($('#documentFrom').val()=="admin"){
  						
  						 data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyCommentDocument').val(),
  								"reply":true,
  								"admin":true
  						 	};
  						}else{
  							
  							 data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyCommentDocument').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						};
  							
  							
  						}
  						
  					}else{
  						
  						if($('#documentFrom').val()=="admin"){
  						
  							data={
  								"id":$('#documentId').val(),
  								"comment":$('#documentComment').val(),
  								"reply":false,
  								"admin":true
  							};
  						}else{
  							
  							data={
  	  								"id":$('#documentId').val(),
  	  								"comment":$('#documentComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  							};
  							
  						}
  						
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnDocument",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessDocumentComment').css({"display": "none"}); 
			    			 $('#FailureDocumentComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessDocumentComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailureDocumentComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  				
  				/*********************************************CONCEPTS MAPS APPROVE/REJECT SECTION COMMENT SECTION ********************************/
  				
  					$('.commentConcept').click(function(){
  					
  					var Id=$(this).attr('value');
  					
  					var json={
  						"concepMapid":Id	
  					};
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
					  	
			        	contentType: "application/json",
			       		 url: "/loadByConceptComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
					       			 html+= data[i].dateReceived;
					       			 html+= '</p></div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="replyConcept(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					       		
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
							       				 html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataConcept').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					
  					
  					
  					$('#conceptId').prop('value',Id);
  					$('#ConceptCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  				
  				$('#conceptComment').change(function(){
  					
  					
  					$('.commentConceptModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentConceptModal,.commentConceptModalReply').click(function(){
  					
 
  					var data;
  					
  					if($(this).prop('name')=="reply"){
  						
  						if($('#conceptFrom').val()=="admin"){
  						
  						 data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyCommentConcept').val(),
  								"reply":true,
  								"admin":true
  						 	};
  						}else{
  							
  							 data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyCommentConcept').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						};
  							
  							
  						}
  						
  					}else{
  						
  						if($('#conceptFrom').val()=="admin"){
  						
  							data={
  								"id":$('#conceptId').val(),
  								"comment":$('#conceptComment').val(),
  								"reply":false,
  								"admin":true
  							};
  						}else{
  							
  							data={
  	  								"id":$('#conceptId').val(),
  	  								"comment":$('#conceptComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  							};
  							
  						}
  						
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnConcept",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessConceptComment').css({"display": "none"}); 
			    			 $('#FailureConceptComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessConceptComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailureConceptComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  				
  				/************************************************QUIZ APPROVE/REJECT SECTION COMMENT SECTION***********************************************/
  				
  				
  				
  				$('.commentQuiz').click(function(){
  					
  					var Id=$(this).attr('value');
  					
  					var json={
  						"quizQuestionId":Id	
  					};
  					
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
					  	
			        	contentType: "application/json",
			       		 url: "/loadByQuizComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
					       			 html+= data[i].dateReceived;
					       			 html+= '</p></div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="replyQuiz(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					       		
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
							       				 html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataQuiz').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					

  					
  					
  					$('#quizId').prop('value',Id);
  					$('#QuizCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  				
  				$('#quizComment').change(function(){
  					
  					
  					$('.commentQuizModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentQuizModal,.commentQuizModalReply').click(function(){
  					
  					
  					var data;
  					
  					if($(this).prop('name')=="reply"){
  						
  						if($('#quizFrom').val()=="admin"){
  						
  						
  						 data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyCommentQuiz').val(),
  								"reply":true,
  								"admin":true
  						};
  						 
  						}else{
  							
  							 data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyCommentQuiz').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						};
  							
  							
  							
  						}
  						
  					}else{
  						
  						if($('#quizFrom').val()=="admin"){
  						data={
  								"id":$('#quizId').val(),
  								"comment":$('#quizComment').val(),
  								"reply":false,
  								"admin":true
  							};
  						}else{
  							
  							data={
  	  								"id":$('#quizId').val(),
  	  								"comment":$('#quizComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  						};
  							
  						}
  						
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnQuiz",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessQuizComment').css({"display": "none"}); 
			    			 $('#FailureQuizComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessQuizComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailureQuizComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  				
  				/************************************************LESSON APPROVE/REJECT SECTION COMMENT SECTION***********************************************/
  				
  				
  				$('.commentLesson').click(function(){
  					
  					var Id=$(this).attr('value');
  					
  					var json={
  						"lessonPlanId":Id	
  					};
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/loadByLessonComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
					       			 html+= data[i].dateReceived;
					       			 html+= '</p></div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="replyLesson(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					   
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
							       				 html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataLesson').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					
  					$('#lessonId').prop('value',Id);
  					$('#LessonCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  				
  				$('#lessonComment').change(function(){
  					
  					
  					$('.commentLessonModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentLessonModal,.commentLessonModalReply').click(function(){
  					
  					
  					var data;
  					
  					if($(this).prop('name')=="reply"){
  						
  						if($('#lessonFrom').val()=="admin"){
  						
  						 data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyCommentLesson').val(),
  								"reply":true,
  								"admin":true
  						 	};
  						}else{
  							
  							data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyCommentLesson').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						 	};
  							
  						}
  						
  					}else{
  						
  						if($('#lessonFrom').val()=="admin"){
  						
  						data={
  								"id":$('#lessonId').val(),
  								"comment":$('#lessonComment').val(),
  								"reply":false,
  								"admin":true
  							};
  						}else{
  							
  							data={
  	  								"id":$('#lessonId').val(),
  	  								"comment":$('#lessonComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  						};
  							
  							
  						}
  						
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnLesson",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessLessonComment').css({"display": "none"}); 
			    			 $('#FailureLessonComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessLessonComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailureLessonComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  				
  				/************************************************PHETS APPROVE/REJECT SECTION COMMENT SECTION***********************************************/
  					
  				
  				$('.commentPhet').click(function(){
  					
  					var Id=$(this).attr('value');
  					
  					var json={
  						"phetId":Id	
  					};
  					
  					var html='';
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
					  	
			        	contentType: "application/json",
			       		 url: "/loadByPhetComment",
			       		 data: JSON.stringify(json),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data){
			       			
			       			 
			       			 
			       			 var len=data.length;
			       			 for(var i=0;i<len;i++){
			       			 
					       			 html+='<div class="card"> <div class="card-body"> <div class="row"> <div class="col-md-2">';
					       			 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
					       			 html+= data[i].dateReceived;
					       			 html+= '</p></div> <div class="col-md-10"> <p><strong>';
					       			 html+= data[i].userName;
					       			 html+= '</strong></p> <div class="clearfix"></div> <p>';
					       			 html+= data[i].comment;
					       			 html+= '</p>';
					       			 html+= '<div class="reply" id='+data[i].comId+'><a href="javascript:void(0)" onclick="replyPhet(this)" com-id=';
					       			 html+= data[i].comId;
					       			 html+= '>Reply</a></div></div></div>';
					       			
					       			 
					       		
					       			 
					       			 var jsonReply={
					       					 "commentid":data[i].comId
					       			 };
					       			 
					       			
					       			
					       			$.ajax({
					       				
									  	type: "POST",
									  	async:false,
							        	contentType: "application/json",
							       		 url: "/loadReplyOnComment",
							       		 data: JSON.stringify(jsonReply),
							       		 beforeSend: function(xhr) {
					                         xhr.setRequestHeader(header, token);
							       		 },
							       		 dataType: 'json',
							       		 cache: false,
							        	 timeout: 600000,
							       		 success: function (dataReply){
							       			 
							       			 var lengthReply=dataReply.length;
							       			 
							       			 for(var j=0;j<lengthReply;j++){
							       				        				 
							       				 
							       				 
							       				 
							       				 html+='<div class="card card-inner"> <div class="card-body"> <div class="row"><div class="col-md-2">';
							       				 html+='<img src="Images/def_face.jpg" class="img img-rounded img-fluid"/> <p class="text-secondary text-center">';
							       				 html+= dataReply[j].dateReceived;
							       				 html+= '</p></div><div class="col-md-10"> <p><strong>';
							       				 html+= dataReply[j].userName;
							       				 html+= '</strong></p> <div class="clearfix"></div> <p>';
								       			 html+= dataReply[j].comment;
								       			 html+= '</p></div></div></div></div>';
								       			 
							       				 
				
							       			 }
							       			 
							       		 },
							       		 error : function(err){
											console.log("not working. ERROR: "+JSON.stringify(err));
							       		 }
							       		 
					       			});
					       			
					       			
					       			html+='</div></div></div></div><br/>';
					       		
					       
					       		
					       			 
			       			 }
			       			 
			       		
		       			 
		       			$('#comDataPhet').html(html);
		       				
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
					});
  					
  					
  					
  					$('#phetId').prop('value',Id);
  					$('#PhetCommentModal').modal('show');
  					
  					
  					
  					
  					
  					
  				})	
  				
  				
  			
  				
  				
  				$('#phetComment').change(function(){
  					
  					
  					$('.commentPhetModal').prop('disabled',false);
  					
  				})
  				
  				
  				
  				$('.commentPhetModal,.commentPhetModalReply').click(function(){
  					
  				
  					var data;
  					
  					if($(this).prop('name')=="reply"){
  						
  						if($('#phetFrom').val()=="admin"){
  							
  							data={
  								"id":$(this).prop('id'),
  								"comment":$('#replyComment').val(),
  								"reply":true,
  								"admin":true
  						 	};
  						}else{
  							
  							 data={
  	  								"id":$(this).prop('id'),
  	  								"comment":$('#replyComment').val(),
  	  								"reply":true,
  	  								"admin":false
  	  						 	};
  							
  						}
  						
  					}else{
  						
  						if($('#phetFrom').val()=="admin"){
  						data={
  								"id":$('#phetId').val(),
  								"comment":$('#phetComment').val(),
  								"reply":false,
  								"admin":true
  						};
  						}else{
  							
  							data={
  	  								"id":$('#phetId').val(),
  	  								"comment":$('#phetComment').val(),
  	  								"reply":false,
  	  								"admin":false
  	  						};
  							
  							
  						}
  						
  					}
  					
  					var token = $("meta[name='_csrf']").attr("content");
  					var header = $("meta[name='_csrf_header']").attr("content");
  					
  					$.ajax({
					  	type: "POST",
			        	contentType: "application/json",
			       		 url: "/uploadCommentOnPhet",
			       		 data: JSON.stringify(data),
			       		 beforeSend: function(xhr) {
	                         xhr.setRequestHeader(header, token);
			       		 },
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			       		 success: function (data1){
			       			 
			       			 $('#SuccessPhetComment').css({"display": "none"}); 
			    			 $('#FailurePhetComment').css({"display": "none"});
			    			
			    			 if(data1[0]==="Success"){
			    				 $('#SuccessPhetComment').css({"display": "block"});
			    			 }else if(data1[0]==="failure"){
			    				 $('#FailurePhetComment').css({"display": "block"});
			    			 }
			       		
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
  					});
  					
  				
  					
  				})
  					
  		/*-----------------------------------------------------------------------END------------------------------------------------------------------------*/			
  					
  			
  				
  	/****************************************************USER SIDE MYACCOUNT SECTION (UPADTING PASSWORD******************************************************/
  		
  			$('#newPassTeacher').change(function(){
  				var confpass=$('#confPassTeacher').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordTeacher').prop('disabled',false);
  					
  				}
  			
  			})
  			
  			$('#confPassTeacher').change(function(){
  				var confpass=$('#newPassTeacher').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordTeacher').prop('disabled',false);
  					
  				}
  			
  			})
  				
  			$('#updatePasswordTeacher').click(function(){
  			
  			var userid=$('#userIdUpdateTeacher').val();
  			var pass=$('#newPassTeacher').val();
  			var confpass=$('#confPassTeacher').val();
  			
  			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
  			if(pass === confpass){
  				
  				var json={
  					"password":pass,
  					"userId":userid
  					
  				};
  				
  			
  				
  				$.ajax({
  					type: "POST",
  					contentType: "application/json",
  					url: "/updatePassword",
  					data:JSON.stringify(json),
  					 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
		       		 },
  					cache:false,
  					timeout: 600000,
  					success:function(data){
  						 
  						 $('#Success').css({"display": "none"}); 
  						 $('#Failure').css({"display": "none"});
  						
  						 if(data[0]==="Success"){
  							 $('#Success').css({"display": "block"});
  							 $('#newPassTeacher').prop('value',"");
  							 $('#confPassTeacher').prop('value',"");
  							 $('#updatePasswordTeacher').prop('disabled',true);
  							 
  							 setTimeout(function() {
  					            $('#Success').fadeOut(1000)}, 4000);
  						 }else if(data[0]==="failure"){
  							 $('#Failure').css({"display": "block"});
  							 $('#newPassTeacher').prop('value',"");
 							 $('#confPassTeacher').prop('value',"");
 							 $('#updatePasswordTeacher').prop('disabled',true);
 							 
 							 setTimeout(function() {
 					            $('#Failure').fadeOut(1000)}, 4000);
  						 }
  						
  					
  					},
  				
  				error : function(err){
  					console.log("not working. ERROR: "+JSON.stringify(err));
  				}
  				
  				});
  				
  				
  			}else{
  				 $('#Success').css({"display": "none"}); 
				 $('#Failure').css({"display": "none"});
				 $('#Failure').css({"display": "block"});
			
				 setTimeout(function() {
			            $('#Failure').fadeOut(1000)}, 4000);
			  
				 
				 $('#newPassTeacher').prop('value',"");
				 $('#confPassTeacher').prop('value',"");
				 
				 $('#updatePasswordTeacher').prop('disabled',true);
  				
  			}
  			
  		})
  		
  /******************************************************************************************************************************/		
  		
  		
  		$('#newPassLearnerParent').change(function(){
  				var confpass=$('#confPassLearnerParent').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordLearnerParent').prop('disabled',false);
  					
  				}
  			
  			})
  			
  			$('#confPassLearnerParent').change(function(){
  				var confpass=$('#newPassLearnerParent').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordLearnerParent').prop('disabled',false);
  					
  				}
  			
  			})
  				
  			$('#updatePasswordLearnerParent').click(function(){
  			
  			var userid=$('#userIdUpdateLearnerParent').val();
  			var pass=$('#newPassLearnerParent').val();
  			var confpass=$('#confPassLearnerParent').val();
  			
  			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
  			if(pass === confpass){
  				
  				var json={
  					"password":pass,
  					"userId":userid
  					
  				};
  				
  				$.ajax({
  					type: "POST",
  					contentType: "application/json",
  					url: "/updatePassword",
  					data:JSON.stringify(json),
  					 beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
		       		 },
  					cache:false,
  					timeout: 600000,
  					success:function(data){
  						 
  						 $('#Success').css({"display": "none"}); 
  						 $('#Failure').css({"display": "none"});
  						
  						 if(data[0]==="Success"){
  							 $('#Success').css({"display": "block"});
  							 $('#newPassLearnerParent').prop('value',"");
  							 $('#confPassLearnerParent').prop('value',"");
  							 $('#updatePasswordLearnerParent').prop('disabled',true);
  						 }else if(data[0]==="failure"){
  							 $('#Failure').css({"display": "block"});
  							 $('#newPassLearnerParent').prop('value',"");
 							 $('#confPassLearnerParent').prop('value',"");
 							 $('#updatePasswordLearnerParent').prop('disabled',true);
  						 }
  						
  					
  					},
  				
  				error : function(err){
  					console.log("not working. ERROR: "+JSON.stringify(err));
  				}
  				
  				});
  				
  				
  			}else{
  				 $('#Success').css({"display": "none"}); 
				 $('#Failure').css({"display": "none"});
				 $('#Failure').css({"display": "block"});
				 
				 $('#newPassLearnerParent').prop('value',"");
				 $('#confPassLearnerParent').prop('value',"");
				 
				 $('#updatePasswordLearnerParent').prop('disabled',true);
  				
  			}
  			
  		})
  				
  				
  				
  	/**********************************************************************************************************************************************************/
  		
  		
  			
  		$('#newPassAdmin').change(function(){
  				var confpass=$('#confPassAdmin').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordAdmin').prop('disabled',false);
  					
  				}
  			
  			})
  			
  			$('#confPassAdmin').change(function(){
  				var confpass=$('#newPassAdmin').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordAdmin').prop('disabled',false);
  					
  				}
  			
  			})
  				
  			$('#updatePasswordAdmin').click(function(){
  			
  			var userid=$('#userIdUpdateAdmin').val();
  			var pass=$('#newPassAdmin').val();
  			var confpass=$('#confPassAdmin').val();
  			
  			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
  			if(pass === confpass){
  				
  				var json={
  					"password":pass,
  					"userId":userid
  					
  				};
  				
  				$.ajax({
  					type: "POST",
  					contentType: "application/json",
  					url: "/updatePassword",
  					data:JSON.stringify(json),
  					beforeSend: function(xhr) {
                         xhr.setRequestHeader(header, token);
		       		},
  					cache:false,
  					timeout: 600000,
  					success:function(data){
  						 
  						 $('#Success').css({"display": "none"}); 
  						 $('#Failure').css({"display": "none"});
  						
  						 if(data[0]==="Success"){
  							 $('#Success').css({"display": "block"});
  							 $('#newPassAdmin').prop('value',"");
  							 $('#confPassAdmin').prop('value',"");
  							 $('#updatePasswordAdmin').prop('disabled',true);
  						 }else if(data[0]==="failure"){
  							 $('#Failure').css({"display": "block"});
  							 $('#newPassAdmin').prop('value',"");
 							 $('#confPassAdmin').prop('value',"");
 							 $('#updatePasswordAdmin').prop('disabled',true);
  						 }
  						
  					
  					},
  				
  				error : function(err){
  					console.log("not working. ERROR: "+JSON.stringify(err));
  				}
  				
  				});
  				
  				
  			}else{
  				 $('#Success').css({"display": "none"}); 
				 $('#Failure').css({"display": "none"});
				 $('#Failure').css({"display": "block"});
				 
				 $('#newPassAdmin').prop('value',"");
				 $('#confPassAdmin').prop('value',"");
				 
				 $('#updatePasswordAdmin').prop('disabled',true);
  				
  			}
  			
  		})
  		
  /**********************************************  END *********************************************************************************/		
  		
  		
  /*****************************************ADD MATERIAL FROM USER SIDE**********************************************************/
  		

  		
  		$('#addArticlefromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addArticle();
  			
  			
  		})
  		
  		$('#descriptionArticle').change(function(){
  			var desc=$(this).val();
  			
  			$("#addArticlefromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#urlArticle').val().length>0 && $('#sourceArticle').val().length>0 && $("#articleUserCheck").is(":checked")){
  				$("#addArticlefromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#urlArticle').change(function(){
  			var desc=$(this).val();
  			
  			$("#addArticlefromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#descriptionArticle').val().length>0 && $('#sourceArticle').val().length>0 && $("#articleUserCheck").is(":checked")){
  				$("#addArticlefromUser").prop('disabled', false);
  			}
  			
  		})
  		
  			$('#sourceArticle').change(function(){
  			var desc=$(this).val();
  			
  			$("#addArticlefromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#urlArticle').val().length>0 && $('#descriptionArticle').val().length>0 && $("#articleUserCheck").is(":checked")){
  				$("#addArticlefromUser").prop('disabled', false);
  			}
  			
  		})
  		
  			$('#articleUserCheck').change(function(){
  				
  				$("#addArticlefromUser").prop('disabled', true);
  				
  	  			if($('#sourceArticle').val().length>0 && $('#urlArticle').val().length>0 && $('#descriptionArticle').val().length>0 && $("#articleUserCheck").is(":checked")){
  	  				$("#addArticlefromUser").prop('disabled', false);
  	  			}
  				
  			})
  		
  		/*****************************************************************************************************************/
  		
  		$('#addDocumentfromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addDocument();
  			
  			
  		})
  		
  		$('#descriptionDocument').change(function(){
  			var desc=$(this).val();
  			
  			$("#addDocumentfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#UrlDocument').val().length>0 && $('#sourceDocument').val().length>0 && $("#documentUserCheck").is(":checked")){
  				$("#addDocumentfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#UrlDocument').change(function(){
  			var desc=$(this).val();
  			
  			$("#addDocumentfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#descriptionDocument').val().length>0 && $('#sourceDocument').val().length>0 && $("#documentUserCheck").is(":checked")){
  				$("#addDocumentfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#sourceDocument').change(function(){
  			var desc=$(this).val();
  			
  			$("#addDocumentfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#UrlDocument').val().length>0 && $('#descriptionDocument').val().length>0 && $("#documentUserCheck").is(":checked")){
  				$("#addDocumentfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#documentUserCheck').change(function(){
  			
  			
  			$("#addDocumentfromUser").prop('disabled', true);
			
  			if($('#descriptionDocument').val().length>0 && desc.length>0 && $('#UrlDocument').val().length>0 && $('#sourceDocument').val().length>0 && $("#documentUserCheck").is(":checked")){
  				$("#addDocumentfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  		/************************************************************************************************************************/
  		
  			$('#addConceptfromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addConcept();
  			
  			
  		})
  		
  		$('#descriptionConceptMap').change(function(){
  			var desc=$(this).val();
  			
  			
  			$("#addConceptfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#conceptMapImage').get(0).files.length >0 && $('#headlineConceptMap').val().length>0 && $("#ConceptUserCheck").is(":checked")){
  				$("#addConceptfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#headlineConceptMap').change(function(){
  			var desc=$(this).val();
  		
  			
  			$("#addConceptfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#descriptionConceptMap').val().length>0 && $('#conceptMapImage').get(0).files.length >0 && $("#ConceptUserCheck").is(":checked")){
  				$("#addConceptfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#conceptMapImage').change(function(){
  			var desc=$(this).val();
  		
  			
  			$("#addConceptfromUser").prop('disabled', true);
			
  			if($('#conceptMapImage').get(0).files.length >0 && $('#headlineConceptMap').val().length>0 && $('#descriptionConceptMap').val().length>0 && $("#ConceptUserCheck").is(":checked")){
  				$("#addConceptfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#ConceptUserCheck').change(function(){
  			
  			$("#addConceptfromUser").prop('disabled', true);
			
  			if($('#descriptionConceptMap').val().length>0 && $('#conceptMapImage').get(0).files.length >0 && $('#headlineConceptMap').val().length>0 && $("#ConceptUserCheck").is(":checked")){
  				$("#addConceptfromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  		
  		/*********************************************************************************************************************/
  		
  		$('#addQuizfromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addQuiz();
  			
  			
  		})
  		
  		$('#remarksQuiz').change(function(){
  			
  			
  			$("#addQuizfromUser").prop('disabled', true);
			
  			if($('#QuestionQuiz').get(0).files.length > 0 && $('#AnswerQuiz').get(0).files.length > 0 && $('#remarksQuiz').length>0 && $("#quizUserCheck").is(":checked")){
  				$("#addQuizfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		$('#QuestionQuiz').change(function(){
  			
  			
  			$("#addQuizfromUser").prop('disabled', true);
			
  			if($('#QuestionQuiz').get(0).files.length > 0 && $('#remarksQuiz').length > 0 && $('#AnswerQuiz').get(0).files.length > 0 && $("#quizUserCheck").is(":checked")){
  				$("#addQuizfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		$('#AnswerQuiz').change(function(){
  			
  			
  			$("#addQuizfromUser").prop('disabled', true);
			
  			if($('#AnswerQuiz').get(0).files.length > 0 && $('#QuestionQuiz').get(0).files.length > 0 && $('#remarksQuiz').length > 0 && $("#quizUserCheck").is(":checked")){
  				$("#addQuizfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		$('#quizUserCheck').change(function(){
  			
  			
  			$("#addQuizfromUser").prop('disabled', true);
			
  			if($('#QuestionQuiz').get(0).files.length > 0 && $('#AnswerQuiz').get(0).files.length > 0 && $('#remarksQuiz').length > 0 && $("#quizUserCheck").is(":checked")){
  				$("#addQuizfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		
  		/********************************************************************************************************************/
  		
  		$('#addLessonfromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addLesson();
  			
  			
  		})
  		
  		$('#lesson').change(function(){
  			
  			
  			$("#addLessonfromUser").prop('disabled', true);
			
  			if($('#lesson').get(0).files.length > 0 && $("#lessonUserCheck").is(":checked")){
  				$("#addLessonfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		$('#lessonUserCheck').change(function(){
  			
  			$("#addLessonfromUser").prop('disabled', true);
			
  			if($('#lesson').get(0).files.length > 0 && $("#lessonUserCheck").is(":checked")){
  				$("#addLessonfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		
  		
  		/************************************************************************************************************************/
  		
  		$('#addPhetfromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addPhet();
  			
  			
  		})
  		
  		$('#descriptionPhet').change(function(){
  			var desc=$(this).val();
  			
  			$("#addPhetfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#Embedphet').val().length>0 && $('#sourcePhet').val().length>0 && $("#phetUserCheck").is(":checked")){
  				$("#addPhetfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		$('#Embedphet').change(function(){
  			var desc=$(this).val();
  			
  			$("#addPhetfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#descriptionPhet').val().length>0 && $('#sourcePhet').val().length>0 && $("#phetUserCheck").is(":checked")){
  				$("#addPhetfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		$('#sourcePhet').change(function(){
  			var desc=$(this).val();
  			
  			$("#addPhetfromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#Embedphet').val().length>0 && $('#descriptionPhet').val().length>0 && $("#phetUserCheck").is(":checked")){
  				$("#addPhetfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  			$('#phetUserCheck').change(function(){
  			var desc=$(this).val();
  			
  			$("#addPhetfromUser").prop('disabled', true);
			
  			if($('#sourcePhet').val().length>0 && $('#Embedphet').val().length>0 && $('#descriptionPhet').val().length>0 && $("#phetUserCheck").is(":checked")){
  				$("#addPhetfromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		/*************************************************************************************************************/
  		
  		$('#addVideofromUser').click(function(){
  			
  			event.preventDefault();
  			
  			addVideo();
  			
  			
  		})
  		
  		$('#descriptionVideo').change(function(){
  			var desc=$(this).val();
  			
  			$("#addVideofromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#urlVideo').val().length>0 && $('#sourceVideo').val().length>0 && $("#videoUserCheck").is(":checked")){
  				$("#addVideofromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#urlVideo').change(function(){
  			var desc=$(this).val();
  			
  			$("#addVideofromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#descriptionVideo').val().length>0 && $('#sourceVideo').val().length>0 && $("#videoUserCheck").is(":checked")){
  				$("#addVideofromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#sourceVideo').change(function(){
  			var desc=$(this).val();
  			
  			$("#addVideofromUser").prop('disabled', true);
			
  			if(desc.length>0 && $('#urlVideo').val().length>0 && $('#descriptionVideo').val().length>0 && $("#videoUserCheck").is(":checked")){
  				$("#addVideofromUser").prop('disabled', false);
  			}
  			
  			
  		})
  		
  			$('#videoUserCheck').change(function(){
  		
  			
  			$("#addVideofromUser").prop('disabled', true);
			
  			if($('#descriptionVideo').val().length>0 && $('#urlVideo').val().length>0 && $('#sourceVideo').val().length>0 && $("#videoUserCheck").is(":checked")){
  				$("#addVideofromUser").prop('disabled', false);
  			}
  			
  		})
  		
  		
  		
  		
  
  		
  		
  		
  /*************************************************END**********************************************************************/
  					
  					
  	});

$(document).ready(function () {                             /************Admin side              */
	  $('#dtBasicExample').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});   

/********************** User Side********************/

$(document).ready(function () {
	  $('#dtBasicExamplePhet').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	  $('#dtBasicExampleVideo').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	  $('#dtBasicExampleLesson').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	  $('#dtBasicExampleDocument').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	  $('#dtBasicExampleArticle').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	  $('#dtBasicExampleQuiz').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	  $('#dtBasicExampleConcept').DataTable();
	  $('.dataTables_length').addClass('bs-select');
});

/********************************************************************/


// -----------------------------------------AJAX FUNCTION FOR TOPIC---------------------------------------------------------------------------
  	
function fire_ajax_submit_Topic(){

		var form=$('#uploadTopic')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
			
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "/updateTopic",
		data:data,
		beforeSend: function(xhr) {
             xhr.setRequestHeader(header, token);
   		},
		cache:false,
		contentType:false,
		processData:false,
		timeout: 600000,
		success:function(data){
			 
			 $('#Success').css({"display": "none"}); 
			 $('#invalid-data').css({"display": "none"}); 
			 $('#Failure').css({"display": "none"});
			
			 if(data[0]==="Success"){
				 $('#Success').css({"display": "block"});
			 }else if(data[0]==="failure"){
				 $('#Failure').css({"display": "block"});
			 }else{
				 $('#invalid-data').css({"display": "block"}); 
			 }
			
		
		},
	
	error : function(err){
		console.log("not working. ERROR: "+JSON.stringify(err));
	}
	
	});
}

/* -------------------------------------------------------END---------------------------------------------------------------------------------*/

/* ---------------------------------------------AJAX FUNCTION FOR QUIZ--------------------------------------------------------------------*/

	function fire_ajax_submit_Quiz(){
	
		var form=$('#uploadQuiz')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateQuiz",
				data:data,
				beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	

	function fire_ajax_submit_QuizOnUser(){
	
		var form=$('#uploadUserQuiz')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateQuiz",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#SuccessQuiz').css({"display": "none"}); 
					 $('#invalid-dataQuiz').css({"display": "none"}); 
					 $('#FailureQuiz').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessQuiz').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#FailureQuiz').css({"display": "block"});
					 }else{
						 $('#invalid-dataQuiz').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
/* -------------------------------------------------------END--------------------------------------------------------------------------------------*/


/* ---------------------------------------------AJAX FUNCTION FOR VIDEO--------------------------------------------------------------------*/
	function fire_ajax_submit_Video(){
		
		var form=$('#uploadVideo')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateVideo",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	function fire_ajax_submit_VideoOnUser(){
		
		var form=$('#uploadUserVideo')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateVideo",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					
					 $('#SuccessVideo').css({"display": "none"}); 
					 $('#invalid-dataVideo').css({"display": "none"}); 
					 $('#FailureVideo').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessVideo').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#FailureVideo').css({"display": "block"});
					 }else{
						 $('#invalid-dataVideo').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
/* -------------------------------------------------------END--------------------------------------------------------------------------------------*/

	
/******************************************AJAX FUNCTION FOR CONCEPT -MAPS *********************************************/
	
	

	function fire_ajax_submit_Concept(){
		
		var form=$('#uploadConcept')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateConcept",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	function fire_ajax_submit_ConceptOnUser(){
		
		var form=$('#uploadUserConcept')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateConcept",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					
					 $('#SuccessConcept').css({"display": "none"}); 
					 $('#invalid-dataConcept').css({"display": "none"}); 
					 $('#FailureConcept').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessConcept').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#FailureConcept').css({"display": "block"});
					 }else{
						 $('#invalid-dataConcept').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	
	
/*--------------------------------------------------END---------------------------------------------------------------*

/* ---------------------------------------------AJAX FUNCTION FOR ARTICLE--------------------------------------------------------------------*/
	function fire_ajax_submit_Article(){
		
		var form=$('#uploadArticle')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateArticle",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	
function fire_ajax_submit_ArticleOnUser(){
		
		var form=$('#uploadUserArticle')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateArticle",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#SuccessArticle').css({"display": "none"}); 
					 $('#invalid-dataArticle').css({"display": "none"}); 
					 $('#FailureArticle').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessArticle').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#FailureArticle').css({"display": "block"});
					 }else{
						 $('#invalid-dataArticle').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
/* -------------------------------------------------------END--------------------------------------------------------------------------------------*/

/* ---------------------------------------------AJAX FUNCTION FOR DOCUMENT--------------------------------------------------------------------*/
	function fire_ajax_submit_Document(){
		
		var form=$('#uploadDocument')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateDocument",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
function fire_ajax_submit_DocumentOnUser(){
		
		var form=$('#uploadUserDocument')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateDocument",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#SuccessDocument').css({"display": "none"}); 
					 $('#invalid-dataDocument').css({"display": "none"}); 
					 $('#FailureDocument').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessDocument').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#FailureDocument').css({"display": "block"});
					 }else{
						 $('#invalid-dataDocument').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
/* -------------------------------------------------------END--------------------------------------------------------------------------------------*/

/* ---------------------------------------------AJAX FUNCTION FOR PHETS--------------------------------------------------------------------*/
	function fire_ajax_submit_Phet(){
		
		var form=$('#uploadPhet')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updatePhet",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	function fire_ajax_submit_PhetOnUser(){
		
		var form=$('#uploadUserPhet')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updatePhet",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#SuccessPhet').css({"display": "none"}); 
					 $('#invalid-dataPhet').css({"display": "none"}); 
					 $('#FailurePhet').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessPhet').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#FailurePhet').css({"display": "block"});
					 }else{
						 $('#invalid-dataPhet').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
/* -------------------------------------------------------END--------------------------------------------------------------------------------------*/

/*-----------------------------------------------AJAX FUNCTION FOR LESSON PLAN-----------------------------------------------------------------------*/
function fire_ajax_submit_Lesson(){
		
		var form=$('#uploadLesson')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/updateLesson",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#Success').css({"display": "none"}); 
					 $('#invalid-data').css({"display": "none"}); 
					 $('#Failure').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#Success').css({"display": "block"});
					 }else if(data[0]==="failure"){
						 $('#Failure').css({"display": "block"});
					 }else{
						 $('#invalid-data').css({"display": "block"}); 
					 }
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}

	function fire_ajax_submit_LessonOnUser(){
	
	var form=$('#uploadUserLesson')[0];
	var data=new FormData(form);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "/updateLesson",
			data:data,
			beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
       		},
			cache:false,
			contentType:false,
			processData:false,
			timeout: 600000,
			success:function(data){
				 
				 $('#SuccessLesson').css({"display": "none"}); 
				 $('#invalid-dataLesson').css({"display": "none"}); 
				 $('#FailureLesson').css({"display": "none"});
				
				 if(data[0]==="Success"){
					 $('#SuccessLesson').css({"display": "block"});
				 }else if(data[0]==="failure"){
					 $('#FailureLesson').css({"display": "block"});
				 }else{
					 $('#invalid-dataLesson').css({"display": "block"}); 
				 }
				
			
			},
		
		error : function(err){
			console.log("not working. ERROR: "+JSON.stringify(err));
			}

	});
}
	
/**************************************************************START OF ADDING MATERIAL TO DATABASE********************************************/
	
/*----------------------------------------------------- ARTICLE --------------------------------------------------------------------------*/
	function addArticle(){
		
		var form=$('#uploadArticle')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/addArticleFromUser",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					 $('#SuccessArticleReturnStatusArticle').css({"display": "none"}); 
					  
					 $('#FailureArticleReturnStatusArticle').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessArticleReturnStatusArticle').css({"display": "block"});
					 }else {
						 $('#FailureArticleReturnStatusArticle').css({"display": "block"});
					 }
					 
					 $('#subjectArticle').prop('disabled',true);
					 
					 $('#topicArticle').prop('disabled',true);
					 $('#descriptionArticle').prop('disabled',true);
					 $('#descriptionArticle').prop('value',"");
					 $('#urlArticle').prop('disabled',true);
					 $('#urlArticle').prop('value',"");
					 $('#sourceArticle').prop('disabled',true);
					 $('#sourceArticle').prop('value',"");
					 
					 
					 
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	
	function addQuiz(){
		
		var form=$('#uploadQuiz')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/addQuizFromUser",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					$('#SuccessArticleReturnStatusQuiz').css({"display": "none"}); 
					  
					 $('#FailureArticleReturnStatusQuiz').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessArticleReturnStatusQuiz').css({"display": "block"});
					 }else {
						 $('#FailureArticleReturnStatusQuiz').css({"display": "block"});
					 }
					 
					 $('#subjectQuiz').prop('disabled',true);
					 
					 $('#topicQuiz').prop('disabled',true);
					 
					 $('#remarksQuiz').prop('disabled',true);
					 $('#remarksQuiz').prop('value',"");
					 
					 $('#QuestionQuiz').prop('disabled',true);
					 $('#QuestionQuiz').prop('value',"");
					 
					 $('#AnswerQuiz').prop('disabled',true);
					 $('#AnswerQuiz').prop('value',"");
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}
	
	
function addLesson(){
		
		var form=$('#uploadLesson')[0];
		var data=new FormData(form);
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/addLessonFromUser",
				data:data,
				beforeSend: function(xhr) {
                     xhr.setRequestHeader(header, token);
	       		},
				cache:false,
				contentType:false,
				processData:false,
				timeout: 600000,
				success:function(data){
					 
					$('#SuccessArticleReturnStatusLesson').css({"display": "none"}); 
					  
					 $('#FailureArticleReturnStatusLesson').css({"display": "none"});
					
					 if(data[0]==="Success"){
						 $('#SuccessArticleReturnStatusLesson').css({"display": "block"});
					 }else {
						 $('#FailureArticleReturnStatusLesson').css({"display": "block"});
					 }
					 
					 $('#subjectLesson').prop('disabled',true);
					 
					 $('#topicLesson').prop('disabled',true);
					 
					 $('#lesson').prop('disabled',true);
					 $('#lesson').prop('value',"");
					 
				
					
				
				},
			
			error : function(err){
				console.log("not working. ERROR: "+JSON.stringify(err));
				}
	
		});
	}

function addPhet(){
	
	var form=$('#uploadPhet')[0];
	var data=new FormData(form);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "/addPhetFromUser",
			data:data,
			beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
       		},
			cache:false,
			contentType:false,
			processData:false,
			timeout: 600000,
			success:function(data){
				 
				$('#SuccessArticleReturnStatusPhet').css({"display": "none"}); 
				  
				 $('#FailureArticleReturnStatusPhet').css({"display": "none"});
				
				 if(data[0]==="Success"){
					 $('#SuccessArticleReturnStatusPhet').css({"display": "block"});
				 }else {
					 $('#FailureArticleReturnStatusPhet').css({"display": "block"});
				 }
				 
				 $('#subjectPhet').prop('disabled',true);
				 
				 $('#topicPhet').prop('disabled',true);
				 
				 $('#descriptionPhet').prop('disabled',true);
				 $('#descriptionPhet').prop('value',"");
				 
				 $('#Embedphet').prop('disabled',true);
				 $('#Embedphet').prop('value',"");
				 
				 $('#sourcePhet').prop('disabled',true);
				 $('#sourcePhet').prop('value',"");
				
			
			},
		
		error : function(err){
			console.log("not working. ERROR: "+JSON.stringify(err));
			}

	});
}


function addDocument(){
	
	var form=$('#uploadDocument')[0];
	var data=new FormData(form);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "/addDocumentFromUser",
			data:data,
			beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
       		},
			cache:false,
			contentType:false,
			processData:false,
			timeout: 600000,
			success:function(data){
				 
				$('#SuccessArticleReturnStatusDocument').css({"display": "none"}); 
				  
				 $('#FailureArticleReturnStatusDocument').css({"display": "none"});
				
				 if(data[0]==="Success"){
					 $('#SuccessArticleReturnStatusDocument').css({"display": "block"});
				 }else {
					 $('#FailureArticleReturnStatusDocument').css({"display": "block"});
				 }
				 
				 $('#subjectDocument').prop('disabled',true);
				 
				 $('#topicDocument').prop('disabled',true);
				 
				 $('#descriptionDocument').prop('disabled',true);
				 $('#descriptionDocument').prop('value',"");
				 
				 $('#UrlDocument').prop('disabled',true);
				 $('#UrlDocument').prop('value',"");
				 
				 $('#sourceDocument').prop('disabled',true);
				 $('#sourceDocument').prop('value',"");
				
			
			},
		
		error : function(err){
			console.log("not working. ERROR: "+JSON.stringify(err));
			}

	});
}

function addConcept(){
	
	var form=$('#uploadConcept')[0];
	var data=new FormData(form);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "/addConceptFromUser",
			data:data,
			beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
       		},
			cache:false,
			contentType:false,
			processData:false,
			timeout: 600000,
			success:function(data){
				 
				$('#SuccessArticleReturnStatusConcept').css({"display": "none"}); 
				  
				 $('#FailureArticleReturnStatusConcept').css({"display": "none"});
				
				 if(data[0]==="Success"){
					 $('#SuccessArticleReturnStatusConcept').css({"display": "block"});
				 }else {
					 $('#FailureArticleReturnStatusConcept').css({"display": "block"});
				 }
				 
				 $('#subjectConcept').prop('disabled',true);
				 
				 $('#topicConcept').prop('disabled',true);
				 
				 $('#descriptionConceptMap').prop('disabled',true);
				 $('#descriptionConceptMap').prop('value',"");
				 
				 $('#headlineConceptMap').prop('disabled',true);
				 $('#headlineConceptMap').prop('value',"");
				 
				 $('#conceptMapImage').prop('disabled',true);
				 $('#conceptMapImage').prop('value',"");
				
			
			},
		
		error : function(err){
			console.log("not working. ERROR: "+JSON.stringify(err));
			}

	});
}

function addVideo(){
	
	var form=$('#uploadVideo')[0];
	var data=new FormData(form);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "/addVideoFromUser",
			data:data,
			beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
       		},
			cache:false,
			contentType:false,
			processData:false,
			timeout: 600000,
			success:function(data){
				
				$('#SuccessArticleReturnStatusVideo').css({"display": "none"}); 
				  
				 $('#FailureArticleReturnStatusVideo').css({"display": "none"});
				
				 if(data[0]==="Success"){
					 $('#SuccessArticleReturnStatusVideo').css({"display": "block"});
				 }else {
					 $('#FailureArticleReturnStatusVideo').css({"display": "block"});
				 }
				 
				 $('#subjectVideo').prop('disabled',true);
				 
				 $('#topicVideo').prop('disabled',true);
				 
				 $('#descriptionVideo').prop('disabled',true);
				 $('#descriptionVideo').prop('value',"");
				 
				 $('#urlVideo').prop('disabled',true);
				 $('#urlVideo').prop('value',"");
				 
				 $('#sourceVideo').prop('disabled',true);
				 $('#sourceVideo').prop('value',"");
				
			
			},
		
		error : function(err){
			console.log("not working. ERROR: "+JSON.stringify(err));
			}

	});
}



/**************************   comment Reply visibility for all Content  *********************************************/

function reply(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentVideoModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentVideoModalReply').attr('id'));
	

	$('.replyRowVideo').insertAfter($(caller));
	$('.replyRowVideo').show();
}

function replyDocument(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentDocumentModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentDocumentModalReply').attr('id'));
	

	$('.replyRowDocument').insertAfter($(caller));
	$('.replyRowDocument').show();
}

function replyArticle(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentArticleModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentArticleModalReply').attr('id'));
	

	$('.replyRowArticle').insertAfter($(caller));
	$('.replyRowArticle').show();
}

function replyPhet(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentPhetModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentPhetModalReply').attr('id'));
	

	$('.replyRowPhet').insertAfter($(caller));
	$('.replyRowPhet').show();
}

function replyQuiz(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentQuizModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentQuizModalReply').attr('id'));
	replyConcept

	$('.replyRowQuiz').insertAfter($(caller));
	$('.replyRowQuiz').show();
}

function replyLesson(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentLessonModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentLessonModalReply').attr('id'));
	

	$('.replyRowLesson').insertAfter($(caller));
	$('.replyRowLesson').show();
}

function replyConcept(caller){
	
	var commentid=$(caller).attr('com-id');
	$('.commentConceptModalReply').attr('id', commentid);
//	alert("1:"+commentid);
//	alert("2:"+$('.commentLessonModalReply').attr('id'));
	

	$('.replyRowConcept').insertAfter($(caller));
	$('.replyRowConcept').show();
}


function validateEmail($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  return emailReg.test( $email );
}
	
/*-----------------------------------------------------END-----------------------------------------------------------------------------------------*/
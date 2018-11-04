//US#5678 : All functions of the Config 

/**
 
* @param dependencies {Array} name of modules this code depends on. Can exclude ".js"
 
* @param callback {Function} function containing this module's functionality.
 
* @version Fri Feb 25 18:44:56 2011
 */

require([], function(rtq) {
  
  /*
   
   * Put all functions for homepage here
   
   */

  
  // this function runs when the page loads
  
  require.ready(function() {
	  
       $('#singleSelectArray .cell-select_singleSelectArray input').click(function(){
			                   $("#selectedIndex_singleSelectArray").val($(this).val());
	  
       });
	 
      $("#previous").click(
	 
      function(){
		 
         $("#previousFlag").val('Yes');
		 
         $("#update").click();
	 
       });
	 
      $("#next").click(
	 
      function(){
		 
         $("#nextFlag").val('Yes');
		 
         $("#update").click();
	 
       });
  
    //US#1234 this function runs when the part search flag changes 
	
      $("#partSeacrhFlag").change(
	 
        function(){
		
            $("#update").click();
	 
       }
	 
      );
	
  
   });

});

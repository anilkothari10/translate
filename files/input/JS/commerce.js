//US#1234 : All functions of the Commerce Page 

/**

* @version Fri Dec  2 14:43:33 2011

**/

require(["return_to_quote_button", "commerce_ids", "jquery_cookie"], function(rtq) {



var base_url = base_url || "/bmfsweb/" + _BM_HOST_COMPANY + "/image/javascript";

var commissionTotalURL = base_url + "/commissionTotalsChart.js";
$.getScript(commissionTotalURL, function(){
});

var commissionPriceTotalsChartURL = base_url + "/commissionPriceTotalsChart.js";
$.getScript(commissionPriceTotalsChartURL, function(){
});

var totalMarginChartjsURL = base_url + "/totalMarginChartJS_quote.js";
$.getScript(totalMarginChartjsURL, function(){
});

var costDistributionjsURL = base_url + "/costDistributionJS_quote.js";
$.getScript(costDistributionjsURL, function(){
});

var totalCostChartURL = base_url + 	"/totalCostChart.js";
$.getScript(totalCostChartURL, function(){
});

var analysisChartURL = base_url + "/analysisChart.js";
$.getScript(analysisChartURL, function(){
});

var marginChartURL = base_url + "/marginChart.js";
$.getScript(marginChartURL, function(){
});

var commissionURL = base_url + "/commission.js";
$.getScript(commissionURL, function(){
});

 //this function runs when the page loads

require.ready(function() {

rtq.set_cookie_in_commerce();

});

});
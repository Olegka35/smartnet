$(document).ready(function() {
   $.getJSON("/net/1", function(result) {
        console.log(result);
      /*$.each(result, function(key,value) {
         $("#productsJson").append(value.id+" "+value.name+" ");
      });*/
   });
});
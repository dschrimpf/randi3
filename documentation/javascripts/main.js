   $(function() {
       $('.menuLink').click(function(event){
                    
           var target = event.target;  
          $.get(target, function(data) {
          var xml =( new XMLSerializer()).serializeToString(data)   
             $('#content').replaceWith(xml)              
          });                   
          
        return false;
        });

        });

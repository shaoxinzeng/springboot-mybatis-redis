// JavaScript Document
        var ohtml = document.documentElement;
        getSize();

        window.onresize = function(){
            getSize();
        }
        function getSize(){

            var screenWidth = ohtml.clientWidth;
            if(screenWidth <= 320){
                ohtml.style.fontSize = '17px';
            }else if(screenWidth >= 750){
                ohtml.style.fontSize = '40px';
            }else{
                ohtml.style.fontSize = screenWidth/(750/40)+'px';
            }
            
        }
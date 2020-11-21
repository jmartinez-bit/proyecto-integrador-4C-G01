
$('.filtro-opciones').css('display','none');

$('.icono-filtro').click(function(){
    $('.filtros').addClass('cerrar-filtro-clase');
    $('.overlay').addClass('mostrar-overlay');
});

$('.cerrar-filtro i').click(function(){
    $('.filtros').removeClass('cerrar-filtro-clase');
    $('.overlay').removeClass('mostrar-overlay');
});

$('.filtro').click(function(){
    $(this).children('.filtro-opciones').slideToggle();
    $(this).children('h4').children('.mostrar-filtro').toggleClass('rotacion');

    $(this).children('.filtro-opciones').children('.opcion').click(function(){
        if($(this).hasClass('color-opcion')){
            $(this).css('color','black');
        }
        else{
            $(this).parent('.filtro-opciones').children('.opcion').css('color','black');
            $(this).css('color','blue');
            
        }
        
    });
    
});


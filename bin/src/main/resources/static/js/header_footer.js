$('.barra i').click(function(){
    $('.sidebar').addClass('mostrar-sidebar');
    $('.boton-cerrar').addClass('mostrar-boton-cerrar');
    $('.overlay').addClass('mostrar-overlay');
});

$('.boton-cerrar').click(function(){
    $('.sidebar').removeClass('mostrar-sidebar');
    $('.boton-cerrar').removeClass('mostrar-boton-cerrar');
    $('.overlay').removeClass('mostrar-overlay');
});

$('.mostrar-buscador i').click(function(){
    $('.buscador').css('display','flex');
});
$('#cancelar').click(function(){
    $('.buscador').css('display','none');
});

$('.categorias2').click(function(){
    $('.categorias2-hover').slideToggle();
    $(this).children('i').toggleClass('rotacion-categoria');
});

$(function(){
    $(window).scroll(function(){
        if($(this).scrollTop()){
            $('.header1').addClass('sticky');
            $('.header2').css('padding','0 45px');
            $('.header2').addClass('sticky2');
        }else{
            $('.header1').removeClass('sticky');
            $('.header2').css('padding','0 30px');
            $('.header2').removeClass('sticky2');
        }
    });

});
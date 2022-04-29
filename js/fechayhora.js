
function relojFecha(){
    var mydate=new Date();var year=mydate.getYear();
    if (year < 1000)year+=1900;
    var day=mydate.getDay();
    var month=mydate.getMonth();
    var daym=mydate.getDate();
    if (daym<10)daym="0"+daym;
    var dayarray=new Array("Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sábado");
    var montharray=new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto",
                                                 "Septiembre","Octubre","Noviembre","Diciembre");
    var horas = mydate.getHours();
    horas = (horas<10)?"0"+horas:horas;
    var minutos = mydate.getMinutes();
    minutos = (minutos<10)?"0"+minutos:minutos;
    var segundos = mydate.getSeconds();
    segundos = (segundos<10)?"0"+segundos:segundos;
    document.getElementById("idReloj").innerHTML = "<"+"font color='white'>"+
                                                    dayarray[day]+", "+daym+" de "+montharray[month]+" de "+
                                                       year+"<"+"/font>";
    setTimeout('relojFecha()',1000);
}
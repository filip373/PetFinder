$(document).ready(function(){
    $('.collapsible').collapsible({
        accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });

    $('select').material_select();

    $("body").on("submit","#searchForm",function (e) {
        e.preventDefault();

        $.ajax({
            type: "GET",
            url: "http://localhost:8080/searchResult",
            data: $(this).serialize(),
            success: function (data) {
                $('#Ads').html(data);
            }
        });
    });
});
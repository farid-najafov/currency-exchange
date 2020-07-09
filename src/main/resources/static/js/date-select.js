$("#date-from")
    .datepicker({
        dateFormat: "dd MMMM yyyy",
        beforeShow: function () {
            $("#ui-datepicker-div")
                .removeClass("datepicker-right")
                .addClass("datepicker-left");
        },
        onSelect: function () {
            const dateObject = $(this).datepicker("getDate");
            const month = dateObject.toLocaleDateString("en-US", {month: "long"});
            const day = dateObject.getDate();
            const year = dateObject.getFullYear();
            $("#date-from-value").html(
                `<span class="datepicker-value-date">${day}</span><span class="datepicker-value-month">${month}</span>
              <span class="datepicker-value-year">${year}</span>`
            );
        },
    })
  .position({ my: "center" });
$("#date-to").datepicker({
    dateFormat: "dd MMMM yyyy",
    beforeShow: function () {
        $("#ui-datepicker-div")
            .removeClass("datepicker-right")
            .addClass("datepicker-left");
    },
    onSelect: function () {
        const dateObject = $(this).datepicker("getDate");
        const month = dateObject.toLocaleDateString("en-US", {month: "long"});
        const day = dateObject.getDate();
        const year = dateObject.getFullYear();
        $("#date-to-value").html(
            `<span class="datepicker-value-date">${day}</span><span class="datepicker-value-month">${month}</span>
              <span class="datepicker-value-year">${year}</span>`
        );
    },
});
$("#date-from1")
    .datepicker({
        dateFormat: "dd MM yy",
        beforeShow: function () {
            $("#ui-datepicker-div")
                .removeClass("datepicker-right")
                .addClass("datepicker-left");
        },
        onSelect: function () {
            const dateObject = $(this).datepicker("getDate");
            const month = dateObject.toLocaleDateString("en-US", {month: "long"});
            const day = dateObject.getDate();
            const year = dateObject.getFullYear();
            $("#date-from-value").html(
                `<span class="datepicker-value-date">${day}</span><span class="datepicker-value-month">${month}</span>
              <span class="datepicker-value-year">${year}</span>`
            );
        },
    })
    .position({my: "center"});
$("#date-to1")
    .datepicker({
        dateFormat: "dd MM yy",
        beforeShow: function () {
            $("#ui-datepicker-div")
                .removeClass("datepicker-right")
                .addClass("datepicker-left");
        },
        onSelect: function () {
            const dateObject = $(this).datepicker("getDate");
            const month = dateObject.toLocaleDateString("en-US", {month: "long"});
            const day = dateObject.getDate();
            const year = dateObject.getFullYear();
            $("#date-to-value").html(
                `<span class="datepicker-value-date">${day}</span><span class="datepicker-value-month">${month}</span>
              <span class="datepicker-value-year">${year}</span>`
            );
        },
    })
    .position({my: "center"});


window.addEventListener("DOMContentLoaded", () => {
    const today = new Date();
    $("#date-from-value, #date-to-value").html(
        `<span class="datepicker-value-date">${today.getDate()}</span><span class="datepicker-value-month">${today.toLocaleDateString(
            "en-US",
            {month: "long"}
        )}</span>`
    );
});

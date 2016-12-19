$(document).ready(function () {
    $("#taskList tr").click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
    });

    $('#taskSubmit').submit(function () { //listen for submit event
        if ($("#taskList tr.selected td:first").html() === undefined) {
            var input = $("<input>")
                    .attr("type", "hidden")
                    .attr("name", "taskKey").val("noKey");
            $('#taskSubmit').append($(input));
        } else {
            var input = $("<input>")
                    .attr("type", "hidden")
                    .attr("name", "taskKey").val($("#taskList tr.selected td:first").html());

            $('#taskSubmit').append($(input));
        }
    });

    $("#taskList tr").dblclick(function () {
        $(this).addClass('selected').siblings().removeClass('selected');

        method = "get";
        path = "Controller";

        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("action", path);

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "action");
        hiddenField.setAttribute("value", "taskDescription");
        form.appendChild(hiddenField);

        var hiddenField2 = document.createElement("input");
        hiddenField2.setAttribute("type", "hidden");
        hiddenField2.setAttribute("name", "taskKey");
        hiddenField2.setAttribute("value", $("#taskList tr.selected td:first").html());
        form.appendChild(hiddenField2);

        document.body.appendChild(form);
        form.submit();
    });

    $("#calendarTable tr td").click(function () {
        var value = parseInt($(this).text().toString());
        if (!isNaN(value)) {
            $(".selected").removeClass('selected');
            $(this).addClass('selected');

            method = "get";
            path = "Controller";

            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", path);

            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "action");
            hiddenField.setAttribute("value", "taskMonthly");
            form.appendChild(hiddenField);

            var hiddenField2 = document.createElement("input");
            hiddenField2.setAttribute("type", "hidden");
            hiddenField2.setAttribute("name", "currentDay");
            hiddenField2.setAttribute("value", value);
            form.appendChild(hiddenField2);

            document.body.appendChild(form);
            form.submit();
        }
    });
});
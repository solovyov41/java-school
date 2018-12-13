$(document).ready(function () {
    $('input:radio').on('change', function (event) {
        $('input:checkbox').each(function () {
            this.checked = false;
        })
    });

    $('input:checkbox.driverCheck').on('change', function (event) {
        let correspondVehicle = $(this).closest("tr.vehicleCheck");
        if (correspondVehicle.find(":radio").prop("checked") === true) {
            let limit = correspondVehicle.find(".passSeatsNum").html();
            let actual = $(this).closest("td").find(':checked').length;
            if (actual > limit) {
                this.checked = false;
            }
        } else {
            this.checked = false;
        }
    });
});


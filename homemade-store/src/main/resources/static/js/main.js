function updateCartQuantities() {
    $("table > tbody > tr").each(function () {
        let currentRow = $(this); //Do not search the whole HTML tree twice, use a subtree instead
        let decorationId = currentRow.find("td:nth-child(1)").text();
        console.log("ID:" + decorationId);
        let quantity = currentRow.find("td:nth-child(2).quantity").val();
        console.log("quantity:" + quantity);
        let token = $('#token').val();

        $.ajax({
            url: '/cart/update?decorationId' + decorationId + '&qty=' + quantity,
            type: 'POST',
            headers: {
                'X-CSRF-Token': token
            },
            contentType: 'application/json',
            success: function (result) {
                location.reload();
            },
            fail: function (result) {
                location.reload();
            }
        });
    });
}
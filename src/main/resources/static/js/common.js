var ajaxWrapper = function (url, type, data, success) {
    $.ajax({
        url: url,
        type: type,
        dataType: "json",
        data: data,
        success: success
    });
};
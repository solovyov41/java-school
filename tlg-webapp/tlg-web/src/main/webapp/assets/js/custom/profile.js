$(document).ready(function () {
    var currentAvatar = $("#avatar").val();
    console.log(currentAvatar);
    var avatarLink = currentAvatar === "" ? contextPath + '/assets/admin/images/users/avatar-1.jpg' : amazonUrl + currentAvatar;
    console.log(avatarLink);
    $("#avatar-1").fileinput({
        language: locale,
        overwriteInitial: true,
        maxFileSize: 1500,
        showClose: false,
        showCaption: false,
        browseLabel: '',
        removeLabel: '',
        browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
        removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
        removeTitle: '',
        elErrorContainer: '#kv-avatar-errors-1',
        msgErrorClass: 'alert alert-block alert-danger',

        defaultPreviewContent: '<img src="' + avatarLink + '">',
        layoutTemplates: {main2: '{preview} {remove} {browse}'},
        allowedFileExtensions: ["jpg", "png", "gif"]
    });
});

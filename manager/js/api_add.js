

function api_add() {
    var application_id = GetRequest()["app_id"];
    api_change(application_id, null, null, null, null);
}
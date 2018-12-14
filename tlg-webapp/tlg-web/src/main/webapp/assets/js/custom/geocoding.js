ymaps.ready(function () {
    var suggestView = new ymaps.SuggestView('cityName');

    var lat = $("#cityLat").val(),
        lng = $("#cityLng").val();

    console.log(lat, lng);

    if (lat === 0 && lng === 0 || lat === "" && lng === "") {
        lat = 59.939095;
        lng = 30.315868;
    } else {
        var myPlacemark = new ymaps.Placemark([lat, lng]);
        myPlacemark.options.set('preset', 'islands#darkBlueDotIconWithCaption');
    }

    var myMap = new ymaps.Map("YMapsID", {
        center: [lat, lng],
        zoom: 10
    });


    if (myPlacemark) {
        myMap.geoObjects.add(myPlacemark);
    }

    suggestView.events.add('select',
        function (event) {
            console.log(event.get('item').value);

            checkCity(event.get('item').value, myMap);
        });
    // $("#mainForm").submit(checkForm(event));

    // $("#cityName").change(checkCity(event));
});

function checkCity(value, map) {
    var city = value.replace(/^\s+|\s+$/g, '');
    if (city) {
        // Поиск координат центра города
        ymaps.geocode(city).then(function (res) {
            // Выбираем первый результат геокодирования.
            var firstGeoObject = res.geoObjects.get(0),
                error;

            if (firstGeoObject) {
                switch (firstGeoObject.properties.get('metaDataProperty.GeocoderMetaData.kind')) {
                    case 'locality':
                        break;
                    default:
                        error = 'Неверно указан населенный пункт, измените название';
                }
            } else {
                error = 'Населенный пункт не найден, измените название';
            }

            // Если геокодер возвращает пустой массив или неточный результат, то показываем ошибку.
            if (error) {
                showError(error);
                return false;
            } else {
                showResult(firstGeoObject, map);
                return true;
            }
        });
    }
}

function showResult(obj, map) {
    // Удаляем сообщение об ошибке, если найденный адрес совпадает с поисковым запросом.
    $('#cityName').removeClass('input_error');
    $('#notice').css('display', 'none');

    var mapContainer = $('#YMapsID'),
        bounds = obj.properties.get('boundedBy'),
        // Рассчитываем видимую область для текущего положения пользователя.
        mapState = ymaps.util.bounds.getCenterAndZoom(
            bounds,
            [mapContainer.width(), mapContainer.height()]
        ),
        // Координаты геообъекта.
        coords = obj.geometry.getCoordinates();
    // Сохраняем полный адрес для сообщения под картой.
    // address = [obj.getCountry(), obj.getAddressLine()].join(', '),
    // Сохраняем укороченный адрес для подписи метки.
    // shortAddress = [obj.getThoroughfare(), obj.getPremiseNumber(), obj.getPremise()].join(' ');

    obj.options.set('preset', 'islands#darkBlueDotIconWithCaption');
    // // Получаем строку с адресом и выводим в иконке геообъекта.
    obj.properties.set('iconCaption', obj.getAddressLine());
    // Создаём карту.
    createMap(map, mapState, obj);

    $("#cityName").val(obj.properties.get('text'));
    $("#cityLat").val(coords[0]);
    $("#cityLng").val(coords[1]);
}

function showError(message) {

    $('#notice').text(message);
    $('#cityName').addClass('input_error');
    $('#notice').css('display', 'block');
    $('#cityLat').val("");
    $('#cityLng').val("");

}

function createMap(map, state, cityObj) {
    // Если карта еще не была создана, то создадим ее и добавим метку с адресом.
    if (!map) {
        map = new ymaps.Map('YMapsID', state);
        // Если карта есть, то выставляем новый центр карты и меняем данные и позицию метки в соответствии с найденным адресом.
    } else {
        map.geoObjects.removeAll();
        map.setCenter(state.center, state.zoom);
    }
    map.geoObjects.add(cityObj);

}
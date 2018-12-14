ymaps.ready(function () {
    let myMap = new ymaps.Map("YMapsID", {
        center: [59.939095, 30.315868],
        zoom: 10
    });

    drawRoute(myMap);

    let cityInputs = $(":input.city-name");
    $.each(cityInputs, function (k, v) {
        let sv = new ymaps.SuggestView(v);
        sv.events.add('select', function (event) {
            setTimeout(function () {
                // searchCity(event, myMap);
                $(v).change();
            }, 1500);
        });
    });


    let waypointsArea = $('#waypoints');
    let cargoesArea = $('#cargoes');
    var waypoints = [];

    waypointsArea.change(function (event) {
        if (event.target.classList.contains('city-name')) {
            searchCity(event, myMap);
        }
    });


    waypointsArea.click(function (event) {
        editRoute(event, waypointsArea)
    });

    $('#save_route').click(function (event) {
        saveRoute(event, waypoints);
    });

    $('#edit_route').click(function () {
        showRouteForm()
    });

    cargoesArea.click(function (event) {
        editCargoes(event, cargoesArea)
    });

    $('#cargoes').on('change', 'select', function (event) {
        checkCityForCargo(event)
    });

    $("#mainForm").submit(function (event) {
        let cargoesInput = $('.cargoCell');
        for (let i = 0, l = cargoesInput.length; i < l; i++) {
            let idAndName = "cargoes[" + i + "].number";
            let number = cargoesInput.eq(i).find(".cargo-number");
            number.attr("id", idAndName);
            number.attr("name", idAndName);

            idAndName = "cargoes[" + i + "].name";
            number = cargoesInput.eq(i).find(".cargo-name");
            number.attr("id", idAndName);
            number.attr("name", idAndName);

            idAndName = "cargoes[" + i + "].description";
            number = cargoesInput.eq(i).find(".cargo-description");
            number.attr("id", idAndName);
            number.attr("name", idAndName);

            idAndName = "cargoes[" + i + "].weight";
            number = cargoesInput.eq(i).find(".cargo-weight");
            number.attr("id", idAndName);
            number.attr("name", idAndName);

            idAndName = "cargoes[" + i + "].departureWaypoint.position";
            number = cargoesInput.eq(i).find(".cargo-departure");
            number.attr("id", idAndName);
            number.attr("name", idAndName);

            idAndName = "cargoes[" + i + "].destinationWaypoint.position";
            number = cargoesInput.eq(i).find(".cargo-destination");
            number.attr("id", idAndName);
            number.attr("name", idAndName);
        }
    });

});

function drawRoute(map) {
    map.geoObjects.removeAll();

    let cities = $(".waypoint.city-name");
    let cityCollection = new ymaps.GeoObjectCollection(null, {
        preset: 'islands#blueDotIcon',
        strokeWidth: 4,
        geodesic: true
    });
    let route = [];
    for (let i = 0, l = cities.length; i < l; i++) {
        let lat = $(cities[i]).siblings(".latitude").val(),
            lng = $(cities[i]).siblings(".longitude").val();
        if (lat !== "" && lng !== "") {
            let placemark = new ymaps.Placemark([lat, lng]);
            placemark.options.set('iconCaption', cities[i].value);
            cityCollection.add(placemark);
            route.push([lat, lng]);
        }
    }

    if (cityCollection.getLength() > 0) {
        // Добавим коллекцию на карту.
        map.geoObjects.add(cityCollection);
        // Установим карте центр и масштаб так, чтобы охватить коллекцию целиком.
        if (cityCollection.getLength() === 1) {
            map.setBounds(cityCollection.get(0).geometry.getBounds(), {
                checkZoomRange: true
            });
        } else {
            map.setBounds(cityCollection.getBounds(), {
                checkZoomRange: true
            });
        }
    }

    if (route.length > 1) {
        // Создаем ломаную с помощью вспомогательного класса Polyline.
        var myPolyline = new ymaps.Polyline(
            // Указываем координаты вершин ломаной.
            route, {
                // Описываем свойства геообъекта.
            }, {
                // Задаем опции геообъекта.
                // Отключаем кнопку закрытия балуна.
                // balloonCloseButton: false,
                // Цвет линии.
                strokeColor: "#2b33eb",
                // Ширина линии.
                strokeWidth: 4,
                // Коэффициент прозрачности.
                strokeOpacity: 0.8
            });
        // Добавляем линии на карту.
        map.geoObjects.add(myPolyline);
    }
}

function searchCity(event, map) {
    let cityNameField = event.target;
    let latField = $(event.target).siblings(".latitude");
    let lngField = $(event.target).siblings(".longitude");
    let city = cityNameField.value.replace(/^\s+|\s+$/g, '');
    if (city) {
        // Поиск координат центра города
        ymaps.geocode(city, {
            /**
             * Опции запроса
             * @see https://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/geocode.xml
             */
            kind: 'locality',
            // Если нужен только один результат, экономим трафик пользователей.
            results: 1
        }).then(function (res) {
            // Выбираем первый результат геокодирования.
            let firstGeoObject = res.geoObjects.get(0),
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
                $(cityNameField).addClass('input_error');
                latField.val("");
                lngField.val("");
                return false;
            } else {
                $(cityNameField).removeClass('input_error');

                // Координаты геообъекта.
                let coords = firstGeoObject.geometry.getCoordinates();

                // // Область видимости геообъекта.
                // bounds = firstGeoObject.properties.get('boundedBy');
                // // Получаем строку с адресом и выводим в иконке геообъекта.
                // firstGeoObject.properties.set('iconCaption', firstGeoObject.getAddressLine());
                //
                // firstGeoObject.options.set('preset', 'islands#blueDotIcon');
                //
                // // // Добавляем первый найденный геообъект на карту.
                // map.geoObjects.add(firstGeoObject);
                // // // Масштабируем карту на область видимости геообъекта.
                // map.setBounds(bounds, {
                //     // Проверяем наличие тайлов на данном масштабе.
                //     checkZoomRange: true
                // });

                cityNameField.value = firstGeoObject.properties.get('text');
                latField.val(coords[0]);
                lngField.val(coords[1]);

                drawRoute(map);
                return true;
            }

        });
    }
}

function newRow($table, cols) {
    $row = $('<tr/>');
    for (let i = 0; i < cols.length; i++) {
        $col = $('<td/>').append(cols[i]);
        $row.append($col);
    }
    $table.append($row);
}

function saveRoute(event, waypoints) {
    let waypointsInput = $('.waypoint.city-name');
    let waypointTable = $('#route_table');
    waypointTable.empty();
    waypoints = [];
    for (let i = 0, l = waypointsInput.length; i < l; i++) {
        if (waypointsInput[i].value === "") {
            return;
        }
        //Обновление id и name для связывания в контроллере
        let cityIdAndName = "waypoints[" + i + "].city.name";
        waypointsInput[i].id = cityIdAndName;
        waypointsInput[i].name = cityIdAndName;

        let posIdAndName = "waypoints[" + i + "].position";
        let posElem = $(waypointsInput[i]).siblings(".position");
        posElem.val(i);
        posElem.attr("id", posIdAndName);
        posElem.attr("name", posIdAndName);
        waypoints.push(waypointsInput[i].value);

        let latIdAndName = "waypoints[" + i + "].city.latitude";
        let latElem = $(waypointsInput[i]).siblings(".latitude");
        if (latElem.val() === "") {
            return;
        }
        latElem.attr('id', latIdAndName);
        latElem.attr('name', latIdAndName);

        let lngIdAndName = "waypoints[" + i + "].city.longitude";
        let lngElem = $(waypointsInput[i]).siblings(".longitude");
        if (lngElem.val() === "") {
            return;
        }
        lngElem.attr('id', lngIdAndName);
        lngElem.attr('name', lngIdAndName);

        newRow(waypointTable, [i + 1, waypointsInput[i].value]);
    }

    $('#route_fixed').show();
    $('#cargoes').show();
    $('#route').hide();

    let depSelect = $('.departure.city-name');
    let destSelect = $('.destination.city-name');

    $.each(depSelect, function (index, val) {
        let defVal = $(val).find("[value='-1']").text();
        $('option', val).remove();
        val.append(new Option(defVal, "-1", true, true));
    });

    $.each(destSelect, function (index, val) {
        let defVal = $(val).find("[value='-1']").text();
        $('option', val).remove();
        val.append(new Option(defVal, "-1", true, true));
    });

    $.each(waypoints, function (val, text) {
        // val += 1;
        if (val !== waypoints.length - 1) {
            depSelect.append(new Option((val + 1) + ". " + text, val, false, false));
        }
        if (val !== 0) {
            destSelect.append(new Option((val + 1) + ". " + text, val, false, false));
        }
    });


}

function editRoute(event, waypointsArea) {
    if (event.target.classList.contains('deleteBtn')) {
        $(event.target).closest(".input-group").remove();
        if (waypointsArea.children('.input-group').length === 2) {
            $('.deleteBtn').hide();
        }
    }

    if (event.target.classList.contains('addBtn')) {
        let waypointDiv = document.createElement("div");
        waypointDiv.className = "form-group input-group";

        let wpBtnsDiv = document.createElement("div");
        wpBtnsDiv.className = "input-group-append";

        let deleteBtn = document.createElement("button");
        deleteBtn.className = "btn btn-outline-secondary deleteBtn";
        let deleteSpan = document.createElement("i");
        deleteSpan.className = "fa fa-times deleteBtn";
        deleteSpan.setAttribute('aria-hidden', 'true');
        deleteBtn.appendChild(deleteSpan);
        $('.deleteBtn').show();

        let addBtn = document.createElement("button");
        addBtn.className = "btn btn-outline-primary addBtn";
        let addSpan = document.createElement("i");
        addSpan.className = "fa fa-plus addBtn";
        addSpan.setAttribute('aria-hidden', 'true');
        addBtn.appendChild(addSpan);

        let input = document.createElement("input");
        input.type = "text";
        input.className = "form-control waypoint city-name";
        input.required = true;

        let posInput = document.createElement("input");
        posInput.type = "hidden";
        posInput.className = "form-control waypoint position";

        let latInput = document.createElement("input");
        latInput.type = "hidden";
        latInput.className = "form-control waypoint latitude";

        let lngInput = document.createElement("input");
        lngInput.type = "hidden";
        lngInput.className = "form-control waypoint longitude";

        waypointDiv.appendChild(input);
        let sv = new ymaps.SuggestView(input);
        sv.events.add('select', function (event) {
                $(input).change();
        });

        waypointDiv.appendChild(posInput);
        waypointDiv.appendChild(latInput);
        waypointDiv.appendChild(lngInput);

        wpBtnsDiv.appendChild(deleteBtn);
        wpBtnsDiv.appendChild(addBtn);
        waypointDiv.appendChild(wpBtnsDiv);

        $(event.target).closest(".input-group").after(waypointDiv);
    }
}

function showRouteForm() {
    $('#exampleModalCenter').modal('hide');
    $('#route_fixed').hide();
    $('#cargoes').hide();
    $('#route').show();
}

function editCargoes(event, cargoesArea) {
    if (event.target.classList.contains('deleteCrgBtn')) {
        $(event.target).closest(".cargo-row").remove();
        if (cargoesArea.find('.cargo-row').length === 1) {
            $('.deleteCrgBtn').hide();
        }
    }

    if (event.target.classList.contains('addCrgBtn')) {

        let cargoRow = $(event.target).closest('.cargo-row');
        let newCargoRow = $(cargoRow).clone();
        $(cargoRow).after(newCargoRow);

        $('.deleteCrgBtn').show();

        let inputFields = $(newCargoRow).find(':input[type=text]');
        $.each(inputFields, function (k, v) {
            v.value = "";
        });

        $.get("/tlg-webapp/manager/cargo/new", function (data) {
            $(newCargoRow).find('.cargo-number').eq(0).val(data.number);
        });

        let selectFields = $(newCargoRow).find('select');
        $.each(selectFields, function (k, v) {
            $(v).val("-1");
        })


    }

}

function checkCityForCargo(event) {
    let val = Number($(event.target).val());
    let thisTd = $(event.target).closest("td");

    if ($(event.target).hasClass("departure")) {
        let destSelect = thisTd.find('.destination');
        let destSelectedVal = Number(destSelect.val());
        if ((destSelectedVal != -1) && (val >= destSelectedVal)) {
            $(event.target).val("-1").prop('selected', true);
        }
    }
    if ($(event.target).hasClass("destination")) {
        let depSelect = thisTd.find('.departure');
        // let depSelectedVal = waypoints.indexOf(depSelect.val());
        let depSelectedVal = Number(depSelect.val());
        if ((depSelectedVal != -1) && (val <= depSelectedVal)) {
            $(event.target).val("-1").prop('selected', true);
        }
    }
}

// //Отправка найденного города на сервер
// $.ajax({
//     url: "/tlg-webapp/manager/city",
//     headers: {
//         'Accept': 'text/html',
//         'Content-Type': 'application/x-www-form-urlencoded'
//     },
//     method: "POST",
//     data: {
//         name: firstGeoObject.properties.get('text'),
//         latitude: coords[0],
//         longitude: coords[1],
//         _csrf: $('meta[name="_csrf"]').attr('content')
//     },
//     dataType: 'application/x-www-form-urlencoded'
// });
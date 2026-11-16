
window.initContractorComplianceScoreboard = function () {
    'use strict';

    var root = document.getElementById('contractorComplianceScoreboard');

    if (!root || root.dataset.initialized === 'true') {
        return;
    }

    var byId = function (id) {
        return root.querySelector('#' + id);
    };

    if (!byId('ccsMonth') || !byId('ccsUnit') ||
        !byId('ccsContractor') || !byId('ccsChart')) {
        return;
    }

    root.dataset.initialized = 'true';

    var month = byId('ccsMonth');
    var unit = byId('ccsUnit');
    var contractor = byId('ccsContractor');
    var status = byId('ccsStatus');

    var chart = null;
    var rows = [];
    var requestNumber = 0;

    var types = ['PF', 'ESIC', 'BANK_WAGE'];
    var names = ['PF', 'ESIC', 'Bank Wage'];
    var colors = ['#378ADD', '#f59e0b', '#16a085'];

    function rating(value) {
        if (value == null || value === '' ||
            !Number.isFinite(Number(value))) {
            return null;
        }
        return Number(value);
    }

    function display(value) {
        return value == null ? 'N/A' : value.toFixed(2) + '/5';
    }

    function safeRows(items) {
        return (Array.isArray(items) ? items : [])
            .filter(function (item) {
                return item &&
                    item.unitId != null &&
                    item.contractorId != null &&
                    types.indexOf(item.complianceType) !== -1;
            })
            .map(function (item) {
				return {
				    unitId: String(item.unitId),
				    unitName: item.unitName || String(item.unitId),
				    contractorId: String(item.contractorId),
				    contractorName: item.contractorName || String(item.contractorId),
                    complianceType: item.complianceType,
                    averageRating: rating(item.averageRating),
                    workmenCount: Number(item.workmenCount) || 0
                };
            });
    }

	function fillSelect(element, values, allLabel) {
	    var selected = element.value;

	    element.replaceChildren(new Option(allLabel, ''));

	    values.sort(function (a, b) {
	        return Number(a) - Number(b);
	    });

	    values.forEach(function (value) {
	        var record = rows.find(function (row) {
	            return element === unit
	                ? row.unitId === value
	                : row.contractorId === value;
	        });

	        var label = value;

	        if (record) {
	            label = element === unit
	                ? record.unitName
	                : record.contractorName;
	        }

	        element.add(new Option(label || value, value));
	    });

	    if (values.includes(selected)) {
	        element.value = selected;
	    }
	}

    function filters() {
        var unitIds = Array.from(
            new Set(rows.map(function (row) {
                return row.unitId;
            }))
        );

        fillSelect(unit, unitIds, 'All Principal Employers');

        var contractorIds = Array.from(
            new Set(
                rows.filter(function (row) {
                    return !unit.value || row.unitId === unit.value;
                }).map(function (row) {
                    return row.contractorId;
                })
            )
        );

        fillSelect(contractor, contractorIds, 'All Contractors');
    }

    function render() {
        var filtered = rows.filter(function (row) {
            return (!unit.value || row.unitId === unit.value) &&
                   (!contractor.value ||
                    row.contractorId === contractor.value);
        });

        var grouped = new Map();

        filtered.forEach(function (row) {
            var key = row.unitId + '|' + row.contractorId;

            if (!grouped.has(key)) {
				grouped.set(key, {
				    unitId: row.unitId,
				    unitName: row.unitName,
				    contractorId: row.contractorId,
				    contractorName: row.contractorName,
				    PF: null,
				    ESIC: null,
				    BANK_WAGE: null
				});
            }

            grouped.get(key)[row.complianceType] = row.averageRating;
        });

        var groups = Array.from(grouped.values()).sort(function (a, b) {
            return Number(a.unitId) - Number(b.unitId) ||
                   Number(a.contractorId) - Number(b.contractorId);
        });

        types.forEach(function (type, index) {
            var values = groups.map(function (group) {
                return group[type];
            }).filter(function (value) {
                return value != null;
            });

            var average = values.length
                ? values.reduce(function (a, b) {
                    return a + b;
                }, 0) / values.length
                : null;

            byId(['ccsPf', 'ccsEsic', 'ccsBank'][index])
                .textContent = display(average);
        });

        var body = byId('ccsTableBody');
        body.replaceChildren();

        if (!groups.length) {
            var emptyRow = document.createElement('tr');
            var emptyCell = document.createElement('td');

            emptyCell.colSpan = 5;
            emptyCell.textContent =
                'No compliance ratings available for the selected period.';

            emptyRow.appendChild(emptyCell);
            body.appendChild(emptyRow);
        }

        groups.forEach(function (group) {
            var tr = document.createElement('tr');

			[
			    group.unitName,
			    group.contractorName,
			    display(group.PF),
			    display(group.ESIC),
			    display(group.BANK_WAGE)
			].forEach(function (value) {
                var td = document.createElement('td');
                td.textContent = value;
                tr.appendChild(td);
            });

            body.appendChild(tr);
        });

        if (chart) {
            chart.destroy();
            chart = null;
        }

        if (typeof Chart !== 'undefined') {
            chart = new Chart(byId('ccsChart'), {
                type: 'bar',
                data: {
                    labels: groups.map(function (group) {
                        return group.unitName + ' / ' + group.contractorName;
                    }),
                    datasets: types.map(function (type, index) {
                        return {
                            label: names[index],
                            backgroundColor: colors[index],
                            data: groups.map(function (group) {
                                return group[type];
                            })
                        };
                    })
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'bottom'
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            min: 0,
                            max: 5,
                            title: {
                                display: true,
                                text: 'Rating (out of 5)'
                            }
                        }
                    }
                }
            });
        } else {
            status.textContent =
                'Chart.js is not loaded; the ratings table is still available.';
        }
    }

    function setRows(data) {
        rows = safeRows(data);
        filters();
        render();
    }

    // Initial data populated by Spring MVC/JSP
	var initial = Array.from(
	    root.querySelectorAll('.ccs-record')
	).map(function (element) {
	    return {
	        unitId: element.dataset.unit,
	        unitName: element.dataset.unitName,
	        contractorId: element.dataset.contractor,
	        contractorName: element.dataset.contractorName,
	        complianceType: element.dataset.type,
	        averageRating: element.dataset.rating,
	        workmenCount: element.dataset.count
	    };
	});

    setRows(initial);

    unit.addEventListener('change', function () {
        filters();
        render();
    });

    contractor.addEventListener('change', render);

    month.addEventListener('change', function () {
        if (!/^\d{4}-\d{2}$/.test(month.value)) {
            return;
        }

        var pieces = month.value.split('-');
        var sequence = ++requestNumber;

        status.textContent = 'Loading compliance ratings...';

        $.ajax({
            url: root.dataset.api,
            type: 'GET',
            dataType: 'json',
            data: {
                year: Number(pieces[0]),
                month: Number(pieces[1])
            },
            success: function (data) {
                if (sequence !== requestNumber) return;

                unit.value = '';
                contractor.value = '';

                setRows(data);
                status.textContent = '';
            },
            error: function (xhr) {
                if (sequence !== requestNumber) return;

                status.textContent = xhr.status === 401
                    ? 'Session expired. Please log in again.'
                    : 'Unable to load compliance ratings. Please try again.';
            }
        });
    });
};

// Full-page load support
if (document.readyState === 'loading') {
    document.addEventListener(
        'DOMContentLoaded',
        window.initContractorComplianceScoreboard
    );
} else {
    window.initContractorComplianceScoreboard();
}

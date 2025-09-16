document.addEventListener("DOMContentLoaded", function () {

    const modalEl = document.getElementById("deleteModal");
    if (!modalEl) {
        console.error("Modal is missing");
        return;
    }

    const deleteModal = new bootstrap.Modal(modalEl);
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
    let entityToDelete = null;
    let rowToDelete = null;
    let entityType = null;


    document.querySelectorAll('.delete-button').forEach(btn => {
        btn.addEventListener('click', (event) => {
            event.stopPropagation();
            entityToDelete = btn.dataset.id;
            entityType = btn.dataset.entity;
            rowToDelete = btn.closest('tr');
            deleteModal.show();
        });
    });

    confirmDeleteBtn.addEventListener('click', () => {
        if (entityToDelete && entityType) {
            fetch(`/${entityType}/${entityToDelete}`, {method: 'DELETE'})
                .then(response => {
                    if (response.ok) {
                        if (rowToDelete) {
                            rowToDelete.remove();
                        } else {
                            window.location.href = `/${entityType}`;
                        }
                    } else {
                        console.error(`Error deleting ${entityType}.`);
                    }
                })
                .catch(error => {
                    console.error('Network error:', error);
                })
                .finally(() => {
                    deleteModal.hide();
                    entityToDelete = null;
                    rowToDelete = null;
                    entityType = null;
                });
        }
    });

    if (!window.location.pathname.includes("/competitors")) {
        return
    }

    const addBtn = document.getElementById("addCoachButton");
    if (addBtn) {
        addBtn.addEventListener("click", function () {
            const competitorId = this.dataset.competitorId;
            const select = document.getElementById("availableCoachSelect");
            const coachId = select.value;

            fetch(`/competitors/${competitorId}/coaches/${coachId}`, {
                method: "PUT"
            }).then(res => {
                if (res.status === 201) {
                    location.reload();
                }
            }).catch(err => console.error(err));
        });
    }

    document.querySelectorAll(".remove-coach-btn").forEach(button => {
        button.addEventListener("click", function () {
            const competitorId = this.dataset.competitorId;
            const coachId = this.dataset.coachId;

            fetch(`/competitors/${competitorId}/coaches/${coachId}`, {
                method: "DELETE"
            }).then(res => {
                if (res.status === 204) {
                    location.reload();
                }
            }).catch(err => console.error(err));
        });
    });

    const addCompetitionButton = document.getElementById("addCompetitionButton");
    if (addCompetitionButton) {
        addCompetitionButton.addEventListener("click", function () {
            const competitorId = this.dataset.competitorId;
            const select = document.getElementById("availableCompetitionsSelect");
            const competitionId = select.value;

            fetch(`/competitors/${competitorId}/competitions/${competitionId}`, {
                method: "PUT"
            }).then(res => {
                if (res.status === 201) {
                    location.reload();
                }
            }).catch(err => console.error(err));
        })
    } else {
        console.error("No competitition button  assigned.")
    }

    document.querySelectorAll(".remove-competition-btn").forEach(button => {
        button.addEventListener("click", function () {
            const competitorId = this.dataset.competitorId;
            const competitionId = this.dataset.competitionId;

            fetch(`/competitors/${competitorId}/competitions/${competitionId}`, {
                method: "DELETE"
            }).then(res => {
                if (res.status === 204) {
                    location.reload();
                }
            }).catch(err => console.error(err));
        })
    })
});

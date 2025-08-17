let competitorToDelete = null;
let rowToDelete = null;

const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

document.querySelectorAll('.delete-button').forEach(btn => {
    btn.addEventListener('click', (event) => {
        event.stopPropagation();
        competitorToDelete = btn.dataset.id;
        rowToDelete = btn.closest('tr');
        deleteModal.show();
    });
});

confirmDeleteBtn.addEventListener('click', () => {
    if (competitorToDelete && rowToDelete) {
        fetch(`/competitors/${competitorToDelete}`, {method: 'DELETE'})
            .then(response => {
                if (response.ok) {
                    rowToDelete.remove();
                } else {
                    console.error('Error deleting Competitor.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            })
            .finally(() => {
                deleteModal.hide();
                competitorToDelete = null;
                rowToDelete = null;
            });
    }
});

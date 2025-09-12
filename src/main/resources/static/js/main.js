let entityToDelete = null;
let rowToDelete = null;
let entityType = null;

const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

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

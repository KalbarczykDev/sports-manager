let competitorToDeleteId = null;
const deleteModal = document.getElementById('deleteModal');
const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
const cancelDeleteBtn = document.getElementById('cancelDeleteBtn');


function showDeleteModal(id) {
    competitorToDeleteId = id;
    deleteModal.style.display = 'flex';
}

function hideDeleteModal() {
    deleteModal.style.display = 'none';
    competitorToDeleteId = null;
}


confirmDeleteBtn.addEventListener('click', () => {
    if (competitorToDeleteId) {
        fetch('competitors/' + competitorToDeleteId, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    console.log('Competitor deleted successfully!');
                    window.location.reload();
                } else {
                    console.error('Error deleting Competitor.');

                }
            })
            .catch(error => {
                console.error('Network error:', error);

            })
            .finally(() => {
                hideDeleteModal();
            });
    }
});


cancelDeleteBtn.addEventListener('click', () => {
    hideDeleteModal();
});


deleteModal.addEventListener('click', (event) => {
    if (event.target === deleteModal) {
        hideDeleteModal();
    }
});
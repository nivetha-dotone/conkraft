;(() => {
  function qs(sel, root) {
    return (root || document).querySelector(sel)
  }
  function qsa(sel, root) {
    return (root || document).querySelectorAll(sel)
  }

  var confirmationModal = qs("#confirmationModal")
  var confirmBtn = qs("#confirmBtn")
  var cancelBtn = qs("#cancelBtn")
  var form = qs("#plantZoneForm")
  var messageContainer = qs("#messageContainer")
  var submitBtn = qs("#submitBtn")

  // Show confirmation modal on form submit
  form.addEventListener(
    "submit",
    (e) => {
      e.preventDefault()
      e.stopPropagation()

      confirmationModal.classList.add("show")
    },
    true,
  )

  // Handle confirm button click
  confirmBtn.addEventListener("click", () => {
    confirmationModal.classList.remove("show")
    submitFormData()
  })

  // Handle cancel button click
  cancelBtn.addEventListener("click", () => {
    confirmationModal.classList.remove("show")
  })

  // Close modal when clicking outside of it
  window.addEventListener("click", (e) => {
    if (e.target === confirmationModal) {
      confirmationModal.classList.remove("show")
    }
  })

  // Submit form data via fetch without redirecting
  function submitFormData() {
    if (submitBtn) submitBtn.setAttribute("disabled", "disabled")

    try {
      var formData = new FormData(form)
      var pairs = []
      formData.forEach((value, key) => {
        pairs.push(encodeURIComponent(key) + "=" + encodeURIComponent(value))
      })
      var body = pairs.join("&")

      fetch(form.action, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
        },
        body: body,
        credentials: "same-origin",
      })
        .then((res) => res.text().then((text) => ({ status: res.status, body: text })))
        .then((response) => {
          // Handle success (200 OK)
          if (response.status === 200) {
            messageContainer.textContent = response.body
            messageContainer.className = "message-container success"

            // Reset all form fields
            form.reset()

            // Auto-hide message after 5 seconds
            setTimeout(() => {
              messageContainer.className = "message-container"
            }, 5000)
          }
          // Handle duplicate entry error (409 Conflict)
          else if (response.status === 409) {
            messageContainer.textContent = response.body || "This entry already exists. Please use different values."
            messageContainer.className = "message-container error"

            // Auto-hide error message after 5 seconds
            setTimeout(() => {
              messageContainer.className = "message-container"
            }, 5000)
          }
          // Handle other errors (500, etc.)
          else {
            messageContainer.textContent = response.body || "Save failed. Please try again."
            messageContainer.className = "message-container error"

            // Auto-hide error message after 5 seconds
            setTimeout(() => {
              messageContainer.className = "message-container"
            }, 5000)
          }
        })
        .catch((err) => {
          messageContainer.textContent = "Save failed. Please try again."
          messageContainer.className = "message-container error"

          // Auto-hide error message after 5 seconds
          setTimeout(() => {
            messageContainer.className = "message-container"
          }, 5000)
        })
        .finally(() => {
          if (submitBtn) submitBtn.removeAttribute("disabled")
        })
    } catch (err) {
      messageContainer.textContent = "Unexpected error occurred."
      messageContainer.className = "message-container error"
      if (submitBtn) submitBtn.removeAttribute("disabled")

      // Auto-hide error message after 5 seconds
      setTimeout(() => {
        messageContainer.className = "message-container"
      }, 5000)
    }
  }
})()

/* =========================================================
   GLOBAL VARIABLES
   ========================================================= */

var chatbotRequestInProgress = false;
var speechRecognition = null;
var chatbotMaximized = false;
var chatbotMinimized = false;

var chatbotSelectedLanguage = "en-IN";
var chatbotSelectedLanguageName = "English";
/* =========================================================
   OPEN CHATBOT
   Called from:
   onclick="openChatBotModal();"
   ========================================================= */

function openChatBotModal() {

    console.log("openChatBotModal called");

    var popupOverlay =
        document.getElementById("chatBotPopupOverlay");

    if (!popupOverlay) {

        popupOverlay = document.createElement("div");

        popupOverlay.id = "chatBotPopupOverlay";
        popupOverlay.className = "chatbot-popup-overlay";

        popupOverlay.innerHTML =
            '<div id="chatBotPopupWindow" class="chatbot-popup-window">' +
                '<div id="chatBotPopupContent" class="chatbot-popup-loading">' +
                    '<div class="chatbot-loading-content">' +
                        '<div class="chatbot-loading-spinner"></div>' +
                        '<div>Loading Conkraft Assistant...</div>' +
                    '</div>' +
                '</div>' +
            '</div>';

        document.body.appendChild(popupOverlay);
    }

    popupOverlay.classList.add("chatbot-visible");

    var popupWindow =
        document.getElementById("chatBotPopupWindow");

    if (popupWindow) {
        popupWindow.classList.add("chatbot-open");
    }

    document.body.style.overflow = "hidden";

    var popupContent =
        document.getElementById("chatBotPopupContent");

    /*
     * Chatbot JSP is already loaded.
     * Do not make another request.
     */
    if (
        popupContent &&
        popupContent.getAttribute("data-loaded") === "true"
    ) {
        initializeConkraftChatbot();
        return;
    }

    loadChatbotJsp();
}


/* =========================================================
   LOAD CHATBOT JSP
   ========================================================= */

function loadChatbotJsp() {

    var popupContent =
        document.getElementById("chatBotPopupContent");

    fetch("/CWFM/showChatBot", {
        method: "GET",
        credentials: "same-origin",
        cache: "no-store"
    })
    .then(function (response) {

        if (!response.ok) {
            throw new Error(
                "Unable to load chatbot. HTTP status: " +
                response.status
            );
        }

        return response.text();
    })
    .then(function (html) {

        popupContent.classList.remove(
            "chatbot-popup-loading"
        );

        popupContent.innerHTML = html;

        popupContent.setAttribute(
            "data-loaded",
            "true"
        );

        initializeConkraftChatbot();
    })
    .catch(function (error) {

        console.error(
            "Chatbot loading error:",
            error
        );

        popupContent.classList.remove(
            "chatbot-popup-loading"
        );

        popupContent.innerHTML =
            '<div class="chatbot-load-error">' +
                'Unable to load the chatbot. Please try again.' +
            '</div>';
    });
}


/* =========================================================
   CLOSE CHATBOT
   ========================================================= */

function closeChatBotModal() {

    stopVoiceRecognition();

    var popupOverlay =
        document.getElementById("chatBotPopupOverlay");

    var popupWindow =
        document.getElementById("chatBotPopupWindow");

    if (popupWindow) {
        popupWindow.classList.remove("chatbot-open");
    }

    setTimeout(function () {

        if (popupOverlay) {
            popupOverlay.classList.remove(
                "chatbot-visible"
            );
        }

        document.body.style.overflow = "";

    }, 200);
}


/* =========================================================
   INITIALIZE CHATBOT
   ========================================================= */

   function initializeConkraftChatbot() {

       var chatbot =
           document.getElementById("conkraftChatBot");

       if (!chatbot) {

           console.error(
               "conkraftChatBot element was not found in chatbot.jsp"
           );

           return;
       }

       restoreChatbotLanguage();

       renderDynamicWelcomeMessage();

       bindChatbotEvents();
       initializeDraggablePopup();
       resizeQuestionInput();
       scrollChatToBottom();

       setTimeout(function () {

           var input =
               document.getElementById("chatQuestion");

           if (input) {
               input.focus();
           }

       }, 100);
   }


/* =========================================================
   BIND EVENTS
   ========================================================= */

function bindChatbotEvents() {

    $("#sendChatButton")
        .off("click.chatbot")
        .on("click.chatbot", function () {
            sendChatMessage();
        });

    $("#chatQuestion")
        .off("keydown.chatbot")
        .on("keydown.chatbot", function (event) {

            if (
                event.key === "Enter" &&
                !event.shiftKey
            ) {
                event.preventDefault();
                sendChatMessage();
            }
        });

    $("#chatQuestion")
        .off("input.chatbot")
        .on("input.chatbot", function () {
            resizeQuestionInput();
        });

    $("#closeChatBotButton")
        .off("click.chatbot")
        .on("click.chatbot", function () {
            closeChatBotModal();
        });

    $("#clearChatButton")
        .off("click.chatbot")
        .on("click.chatbot", function () {
            clearConversation();
        });

    $("#voiceButton")
        .off("click.chatbot")
        .on("click.chatbot", function () {
            startVoiceRecognition();
        });
		
		$("#chatbotLanguage")
		    .off("change.chatbot")
		    .on("change.chatbot", function () {

		        chatbotSelectedLanguage =
		            $(this).val() || "en-IN";

		        chatbotSelectedLanguageName =
		            $("#chatbotLanguage option:selected").text();

		        saveChatbotLanguage();

		        stopVoiceRecognition();

		        updateVoiceButtonTitle();

		        console.log(
		            "Chatbot voice language changed:",
		            chatbotSelectedLanguage,
		            chatbotSelectedLanguageName
		        );
		    });

    $("#minimizeChatBotButton")
        .off("click.chatbot")
        .on("click.chatbot", function () {
            toggleMinimizeChatbot();
        });

    $("#maximizeChatBotButton")
        .off("click.chatbot")
        .on("click.chatbot", function () {
            toggleMaximizeChatbot();
        });

    $(".suggestion-button")
        .off("click.chatbot")
        .on("click.chatbot", function () {

            var question =
                $(this).attr("data-question");

            if (!question) {
                return;
            }

            $("#chatQuestion").val(question);

            resizeQuestionInput();
            sendChatMessage();
        });

    $("#chatBotPopupOverlay")
        .off("click.chatbotOverlay")
        .on("click.chatbotOverlay", function (event) {

            if (
                event.target.id ===
                "chatBotPopupOverlay"
            ) {
                closeChatBotModal();
            }
        });
		
		$("#chatbotLanguage")
		    .off("mousedown.chatbot click.chatbot")
		    .on("mousedown.chatbot click.chatbot", function (event) {

		        /*
		         * Prevent the draggable header from capturing
		         * the dropdown interaction.
		         */
		        event.stopPropagation();
		    })
		    .off("change.chatbot")
		    .on("change.chatbot", function () {

		        chatbotSelectedLanguage =
		            $(this).val() || "en-IN";

		        chatbotSelectedLanguageName =
		            $(this)
		                .find("option:selected")
		                .text();

		        saveChatbotLanguage();
		        stopVoiceRecognition();
		        updateVoiceButtonTitle();

		        console.log(
		            "Selected language:",
		            chatbotSelectedLanguage,
		            chatbotSelectedLanguageName
		        );
		    });
}


/* =========================================================
   SEND MESSAGE
   ========================================================= */

function sendChatMessage() {

    if (chatbotRequestInProgress) {
        return;
    }

    var questionInput =
        document.getElementById("chatQuestion");

    if (!questionInput) {
        console.error("chatQuestion input was not found.");
        return;
    }

    var question =
        questionInput.value.trim();

    if (!question) {
        questionInput.focus();
        return;
    }

    if (question.length > 500) {

        appendBotText(
            "Please limit your question to 500 characters.",
            true
        );

        return;
    }

    appendUserMessage(question);

    questionInput.value = "";
    resizeQuestionInput();

    setRequestState(true);
    showTypingIndicator();

    var chatbot =
        document.getElementById("conkraftChatBot");

    var contextPath = "";

    if (chatbot) {
        contextPath =
            chatbot.getAttribute("data-context-path") || "";
    }

    $.ajax({
        url:  "/CWFM/chatbot/ask",
        type: "POST",

        contentType:
            "application/json; charset=UTF-8",

        dataType: "json",

		data: JSON.stringify({
		    question: question,
		    languageCode: chatbotSelectedLanguage,
		    languageName: chatbotSelectedLanguageName
		}),
        success: function (response) {

            removeTypingIndicator();

            if (!response) {

                appendBotText(
                    "The server returned an empty response.",
                    true
                );

                return;
            }

            renderBotResponse(response);

            if (
                Array.isArray(response.suggestions)
            ) {
                renderSuggestions(
                    response.suggestions
                );
            }
        },

        error: function (
            xhr,
            textStatus,
            errorThrown
        ) {

            removeTypingIndicator();

            console.error(
                "Chatbot request failed:",
                xhr.status,
                xhr.responseText,
                textStatus,
                errorThrown
            );

            var message =
                "Something went wrong while processing your request.";

            if (xhr.status === 0) {

                message =
                    "The server could not be reached.";

            } else if (xhr.status === 400) {

                message =
                    "Invalid request. Check the ChatRequest field names.";

            } else if (xhr.status === 401) {

                message =
                    "Your session has expired. Please log in again.";

            } else if (xhr.status === 403) {

                message =
                    "You do not have permission to use the chatbot.";

            } else if (xhr.status === 404) {

                message =
                    "The chatbot API was not found. Check /chatbot/ask mapping.";

            } else if (xhr.status === 415) {

                message =
                    "Unsupported request format. Check @RequestBody configuration.";

            } else if (xhr.status === 500) {

                message =
                    "A backend error occurred. Check the Tomcat console.";
            }

            appendBotText(message, true);
        },

        complete: function () {

            setRequestState(false);

            if (questionInput) {
                questionInput.focus();
            }
        }
    });
}


/* =========================================================
   RESPONSE HANDLING
   ========================================================= */

   function renderBotResponse(response) {

       var responseType =
           String(response.responseType || "TEXT").toUpperCase();

       switch (responseType) {

           case "CARD":

               /*
                * If CARD contains a list (like ActiveContractorDTO),
                * display it as a table.
                */
               if (Array.isArray(response.data)) {

                   appendBotTable(
                       response.response,
                       response.data,
                       response.success === false
                   );

               } else {

                   appendBotCard(
                       response.response,
                       response.data,
                       response.success === false
                   );
               }

               break;

           case "TABLE":
           case "LIST":

               appendBotTable(
                   response.response,
                   response.data,
                   response.success === false
               );

               break;

           case "TEXT":
           default:

               appendBotText(
                   response.response || "No response was returned.",
                   response.success === false
               );
       }
   }


/* =========================================================
   USER MESSAGE
   ========================================================= */

function appendUserMessage(text) {

    var html =
        '<div class="chat-message user-message">' +
            '<div class="message-wrapper">' +
                '<div class="message-bubble">' +
                    escapeHtml(text) +
                '</div>' +
                '<div class="message-time">' +
                    getCurrentTime() +
                '</div>' +
            '</div>' +
        '</div>';

    $("#chatMessages").append(html);

    scrollChatToBottom();
}


/* =========================================================
   BOT TEXT
   ========================================================= */

function appendBotText(text, isError) {

    var formattedText =
        escapeHtml(text || "")
            .replace(/\r?\n/g, "<br>");

    var errorClass =
        isError
            ? " chat-error-message"
            : "";

    var html =
        '<div class="chat-message bot-message' +
            errorClass +
        '">' +

            '<div class="message-avatar">' +
                '<i class="fas fa-robot"></i>' +
            '</div>' +

            '<div class="message-wrapper">' +
                '<div class="message-bubble">' +
                    formattedText +
                '</div>' +

                '<div class="message-time">' +
                    getCurrentTime() +
                '</div>' +
            '</div>' +

        '</div>';

    $("#chatMessages").append(html);

    scrollChatToBottom();
}


/* =========================================================
   BOT CARD
   ========================================================= */

function appendBotCard(title, data, isError) {

    if (
        !data ||
        typeof data !== "object" ||
        Array.isArray(data)
    ) {

        appendBotText(
            title || "No information available.",
            isError
        );

        return;
    }

    var cardHtml =
        '<div class="chat-response-card">' +
            '<div class="chat-response-card-title">' +
                escapeHtml(title || "Details") +
            '</div>' +
            '<div class="chat-response-card-body">';

    var hasData = false;

    $.each(data, function (key, value) {

        if (
            value === null ||
            typeof value === "undefined" ||
            value === ""
        ) {
            return;
        }

        hasData = true;

        cardHtml +=
            '<div class="chat-response-row">' +
                '<div class="chat-response-label">' +
                    escapeHtml(
                        formatPropertyName(key)
                    ) +
                '</div>' +

                '<div class="chat-response-value">' +
                    escapeHtml(
                        formatValue(value)
                    ) +
                '</div>' +
            '</div>';
    });

    if (!hasData) {

        cardHtml +=
            '<div class="chat-response-value">' +
                'No information available.' +
            '</div>';
    }

    cardHtml +=
            '</div>' +
        '</div>';

    appendBotHtml(cardHtml, isError);
}


/* =========================================================
   BOT TABLE
   ========================================================= */

   function appendBotTable(title, data, isError) {

       if (!Array.isArray(data) || data.length === 0) {

           appendBotText(
               title ? title + "\nNo records found." : "No records found.",
               isError
           );

           return;
       }

       var firstRow = data[0];

       var columns = Object.keys(firstRow);

       /*
        * Contractor table
        */
       if (firstRow.contractorId !== undefined &&
           firstRow.contractorName !== undefined) {

           columns = [
               "contractorId",
               "contractorName"
           ];
       }

       var tableHtml =
           '<div class="chat-response-card">' +

           '<div class="chat-response-card-title">' +
           escapeHtml(title || "Results") +
           ' (' + data.length + ')' +
           '</div>' +

           '<div class="chat-table-wrapper">' +

           '<table class="chat-response-table">' +

           '<thead><tr>';

       $.each(columns, function(i, column) {

           tableHtml +=
               "<th>" +
               escapeHtml(getTableHeading(column)) +
               "</th>";

       });

       tableHtml +=
           "</tr></thead><tbody>";

       $.each(data, function(i, row) {

           tableHtml += "<tr>";

           $.each(columns, function(j, column) {

               tableHtml +=
                   "<td>" +
                   escapeHtml(formatValue(row[column])) +
                   "</td>";

           });

           tableHtml += "</tr>";

       });

       tableHtml +=
           "</tbody></table></div></div>";

       appendBotHtml(tableHtml, isError);
   }
/* =========================================================
   APPEND CUSTOM BOT HTML
   ========================================================= */

function appendBotHtml(contentHtml, isError) {

    var errorClass =
        isError
            ? " chat-error-message"
            : "";

    var html =
        '<div class="chat-message bot-message' +
            errorClass +
        '">' +

            '<div class="message-avatar">' +
                '<i class="fas fa-robot"></i>' +
            '</div>' +

            '<div class="message-wrapper">' +
                contentHtml +

                '<div class="message-time">' +
                    getCurrentTime() +
                '</div>' +
            '</div>' +

        '</div>';

    $("#chatMessages").append(html);

    scrollChatToBottom();
}


/* =========================================================
   TYPING INDICATOR
   ========================================================= */

function showTypingIndicator() {

    removeTypingIndicator();

    var html =
        '<div id="chatTypingIndicator" ' +
             'class="chat-message bot-message">' +

            '<div class="message-avatar">' +
                '<i class="fas fa-robot"></i>' +
            '</div>' +

            '<div class="message-wrapper">' +
                '<div class="message-bubble typing-bubble">' +
                    '<span class="typing-dot"></span>' +
                    '<span class="typing-dot"></span>' +
                    '<span class="typing-dot"></span>' +
                '</div>' +
            '</div>' +

        '</div>';

    $("#chatMessages").append(html);

    scrollChatToBottom();
}

function removeTypingIndicator() {
    $("#chatTypingIndicator").remove();
}


/* =========================================================
   SUGGESTIONS
   ========================================================= */

function renderSuggestions(suggestions) {

    var container =
        $("#chatSuggestions");

    container.empty();

    $.each(suggestions, function (index, item) {

        var question =
            typeof item === "string"
                ? item
                : item.question;

        if (!question) {
            return;
        }

        var button =
            $("<button>", {
                type: "button",
                class: "suggestion-button",
                text: question
            });

        button.attr(
            "data-question",
            question
        );

        container.append(button);
    });

    bindChatbotEvents();
}


/* =========================================================
   CLEAR CHAT
   ========================================================= */

function clearConversation() {

    var confirmed =
        confirm(
            "Do you want to clear this conversation?"
        );

    if (!confirmed) {
        return;
    }

    removeTypingIndicator();

    $("#chatMessages").html(
        '<div class="chat-message bot-message">' +
            '<div class="message-avatar">' +
                '<i class="fas fa-robot"></i>' +
            '</div>' +

            '<div class="message-wrapper">' +
                '<div class="message-bubble">' +
                    'Conversation cleared.<br>' +
                    'How can I help you?' +
                '</div>' +

                '<div class="message-time">' +
                    getCurrentTime() +
                '</div>' +
            '</div>' +
        '</div>'
    );

    $("#chatQuestion").focus();
}


/* =========================================================
   MINIMIZE
   ========================================================= */

function toggleMinimizeChatbot() {

    var popup =
        $("#chatBotPopupWindow");

    chatbotMinimized =
        !chatbotMinimized;

    if (chatbotMinimized) {

        chatbotMaximized = false;

        popup
            .removeClass("chatbot-maximized")
            .addClass("chatbot-minimized");

        $("#minimizeChatBotButton i")
            .removeClass("fa-minus")
            .addClass("fa-window-restore");

    } else {

        popup.removeClass(
            "chatbot-minimized"
        );

        $("#minimizeChatBotButton i")
            .removeClass("fa-window-restore")
            .addClass("fa-minus");

        $("#chatQuestion").focus();
    }
}


/* =========================================================
   MAXIMIZE
   ========================================================= */

function toggleMaximizeChatbot() {

    var popup =
        $("#chatBotPopupWindow");

    chatbotMaximized =
        !chatbotMaximized;

    chatbotMinimized = false;

    popup.removeClass(
        "chatbot-minimized"
    );

    if (chatbotMaximized) {

        popup.addClass(
            "chatbot-maximized"
        );

        $("#maximizeChatBotButton i")
            .removeClass("fa-expand")
            .addClass("fa-compress");

    } else {

        popup.removeClass(
            "chatbot-maximized"
        );

        $("#maximizeChatBotButton i")
            .removeClass("fa-compress")
            .addClass("fa-expand");
    }

    scrollChatToBottom();
}


/* =========================================================
   DRAGGABLE POPUP
   ========================================================= */

   function initializeDraggablePopup() {

       if (typeof $.fn.draggable !== "function") {
           return;
       }

       var popup = $("#chatBotPopupWindow");

       try {

           if (popup.hasClass("ui-draggable")) {
               popup.draggable("destroy");
           }

           popup.draggable({

               handle: ".chatbot-header",

               containment: "window",

               /*
                * Prevent dragging when interacting with
                * buttons, dropdowns and input controls.
                */
               cancel:
                   "button, select, option, textarea, input, " +
                   ".chatbot-language-wrapper, " +
                   ".chatbot-language-select, " +
                   ".chatbot-header-actions",

               start: function () {

                   if (
                       chatbotMaximized ||
                       chatbotMinimized
                   ) {
                       return false;
                   }
               }
           });

       } catch (error) {

           console.warn(
               "Unable to make chatbot draggable:",
               error
           );
       }
   }


/* =========================================================
   VOICE RECOGNITION
   ========================================================= */

   function startVoiceRecognition() {

       var SpeechRecognitionClass =
           typeof SpeechRecognition !== "undefined"
               ? SpeechRecognition
               : (
                   typeof webkitSpeechRecognition !== "undefined"
                       ? webkitSpeechRecognition
                       : null
               );

       if (!SpeechRecognitionClass) {

           appendBotText(
               "Voice recognition is not supported in this browser. Please use the latest version of Google Chrome.",
               true
           );

           return;
       }

       stopVoiceRecognition();

       var languageSelect =
           document.getElementById("chatbotLanguage");

       if (languageSelect) {

           chatbotSelectedLanguage =
               languageSelect.value || "en-IN";

           chatbotSelectedLanguageName =
               languageSelect.options[
                   languageSelect.selectedIndex
               ].text;
       }

       speechRecognition =
           new SpeechRecognitionClass();

       speechRecognition.lang =
           chatbotSelectedLanguage;

       speechRecognition.interimResults = false;
       speechRecognition.continuous = false;
       speechRecognition.maxAlternatives = 1;

       speechRecognition.onstart = function () {

           $("#voiceButton")
               .addClass("listening")
               .attr(
                   "title",
                   "Listening in " +
                   chatbotSelectedLanguageName +
                   "..."
               );

           $("#chatQuestion")
               .attr(
                   "placeholder",
                   "Listening in " +
                   chatbotSelectedLanguageName +
                   "..."
               );
       };

       speechRecognition.onresult = function (event) {

           if (
               !event.results ||
               !event.results[0] ||
               !event.results[0][0]
           ) {
               return;
           }

           var transcript =
               event.results[0][0].transcript;

           $("#chatQuestion").val(transcript);

           resizeQuestionInput();

           console.log(
               "Voice transcript:",
               transcript
           );
       };

       speechRecognition.onerror = function (event) {

           console.error(
               "Voice recognition error:",
               event.error
           );

           var errorMessage =
               getVoiceRecognitionErrorMessage(
                   event.error
               );

           appendBotText(
               errorMessage,
               true
           );
       };

       speechRecognition.onend = function () {

           $("#voiceButton")
               .removeClass("listening");

           updateVoiceButtonTitle();

           $("#chatQuestion")
               .attr(
                   "placeholder",
                   "Ask something about Conkraft..."
               );

           speechRecognition = null;
       };

       try {

           speechRecognition.start();

       } catch (error) {

           console.error(
               "Unable to start voice recognition:",
               error
           );

           appendBotText(
               "Unable to start voice recognition. Please try again.",
               true
           );
       }
   }

   function stopVoiceRecognition() {

       if (!speechRecognition) {
           return;
       }

       try {

           speechRecognition.abort();

       } catch (error) {

           console.log(
               "Voice recognition was already stopped."
           );
       }

       speechRecognition = null;

       $("#voiceButton")
           .removeClass("listening");

       updateVoiceButtonTitle();

       $("#chatQuestion")
           .attr(
               "placeholder",
               "Ask something about Conkraft..."
           );
   }


/* =========================================================
   HELPERS
   ========================================================= */

function setRequestState(inProgress) {

    chatbotRequestInProgress =
        inProgress;

    $("#sendChatButton").prop(
        "disabled",
        inProgress
    );

    $("#chatQuestion").prop(
        "disabled",
        inProgress
    );

    $("#voiceButton").prop(
        "disabled",
        inProgress
    );
}

function resizeQuestionInput() {

    var input =
        document.getElementById("chatQuestion");

    if (!input) {
        return;
    }

    input.style.height = "auto";

    input.style.height =
        Math.min(
            input.scrollHeight,
            90
        ) + "px";
}

function scrollChatToBottom() {

    var container =
        document.getElementById("chatMessages");

    if (!container) {
        return;
    }

    setTimeout(function () {

        container.scrollTop =
            container.scrollHeight;

    }, 20);
}

function getCurrentTime() {

    return new Date().toLocaleTimeString(
        [],
        {
            hour: "2-digit",
            minute: "2-digit"
        }
    );
}

function formatPropertyName(propertyName) {

    if (!propertyName) {
        return "";
    }

    return String(propertyName)
        .replace(
            /([a-z0-9])([A-Z])/g,
            "$1 $2"
        )
        .replace(/_/g, " ")
        .replace(
            /\b\w/g,
            function (character) {
                return character.toUpperCase();
            }
        );
}

function formatValue(value) {

    if (
        value === null ||
        typeof value === "undefined"
    ) {
        return "-";
    }

    if (typeof value === "boolean") {
        return value ? "Yes" : "No";
    }

    if (Array.isArray(value)) {
        return value.join(", ");
    }

    if (typeof value === "object") {
        return JSON.stringify(value);
    }

    return String(value);
}

function escapeHtml(value) {

    return String(
        value == null ? "" : value
    )
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}

function getTableHeading(column) {

    switch (column) {

        case "contractorId":
            return "Contractor ID";

        case "contractorName":
            return "Contractor Name";

        default:
            return formatPropertyName(column);
    }
}

function saveChatbotLanguage() {

    try {

        localStorage.setItem(
            "conkraftChatbotLanguage",
            chatbotSelectedLanguage
        );

    } catch (error) {

        console.warn(
            "Unable to save chatbot language:",
            error
        );
    }
}

function restoreChatbotLanguage() {

    var savedLanguage = "en-IN";

    try {

        savedLanguage =
            localStorage.getItem(
                "conkraftChatbotLanguage"
            ) || "en-IN";

    } catch (error) {

        console.warn(
            "Unable to restore chatbot language:",
            error
        );
    }

    var languageSelect =
        document.getElementById("chatbotLanguage");

    if (!languageSelect) {
        return;
    }

    var languageExists = false;

    for (
        var index = 0;
        index < languageSelect.options.length;
        index++
    ) {

        if (
            languageSelect.options[index].value ===
            savedLanguage
        ) {

            languageExists = true;
            break;
        }
    }

    if (!languageExists) {
        savedLanguage = "en-IN";
    }

    languageSelect.value =
        savedLanguage;

    chatbotSelectedLanguage =
        savedLanguage;

    chatbotSelectedLanguageName =
        languageSelect.options[
            languageSelect.selectedIndex
        ].text;

    updateVoiceButtonTitle();
}

function updateVoiceButtonTitle() {

    var title =
        "Speak in " +
        (
            chatbotSelectedLanguageName ||
            "English"
        );

    $("#voiceButton")
        .attr("title", title);
}

function getVoiceRecognitionErrorMessage(errorCode) {

    switch (errorCode) {

        case "not-allowed":
        case "service-not-allowed":

            return "Microphone permission was denied. Please allow microphone access in your browser.";

        case "no-speech":

            return "No speech was detected. Please speak clearly and try again.";

        case "audio-capture":

            return "No microphone was detected. Please check your microphone.";

        case "network":

            return "Voice recognition could not connect to the recognition service.";

        case "language-not-supported":

            return (
                chatbotSelectedLanguageName +
                " voice recognition is not supported by this browser."
            );

        case "aborted":

            return "Voice recognition was stopped.";

        default:

            return "Voice recognition failed. Please try again.";
    }
}

function renderDynamicWelcomeMessage() {

    var chatbot =
        document.getElementById("conkraftChatBot");

    var welcomeContainer =
        document.getElementById("chatbotWelcomeContent");

    if (!chatbot || !welcomeContainer) {
        return;
    }

	var chatbot = document.getElementById("conkraftChatBot");

	var userName = "User";

	if (chatbot) {
	    userName =  "User";
	}
   

    var greetingInfo =
        getGreetingByCurrentTime();

    welcomeContainer.innerHTML =
        '<div class="welcome-title">' +

            '<span class="welcome-emoji">' +
                escapeHtml(greetingInfo.emoji) +
            '</span>' +

            '<span>' +
                escapeHtml(greetingInfo.greeting) +
                ', ' +
            '</span>' +

            '<span class="welcome-user-name">' +
                escapeHtml(userName.trim()) +
            '</span>' +

            '<span>!</span>' +

        '</div>' +

        '<div class="welcome-subtitle">' +
            'Welcome to ' +
            '<strong>Conkraft AI Assistant</strong>.' +
        '</div>' +

        '<div class="welcome-text">' +
            'I can assist you with your Contract Workforce Management activities.' +
        '</div>' +

        '<div class="welcome-question">' +
            'How may I help you today?' +
        '</div>';
}
function getGreetingByCurrentTime() {

    var currentHour =
        new Date().getHours();

    if (currentHour >= 5 && currentHour < 12) {

        return {
            greeting: "Good Morning",
            emoji: "🌅"
        };
    }

    if (currentHour >= 12 && currentHour < 17) {

        return {
            greeting: "Good Afternoon",
            emoji: "☀️"
        };
    }

    if (currentHour >= 17 && currentHour < 21) {

        return {
            greeting: "Good Evening",
            emoji: "🌇"
        };
    }

    return {
        greeting: "Hello",
        emoji: "👋"
    };
}
function clearConversation() {

    var confirmed =
        confirm(
            "Do you want to clear this conversation?"
        );

    if (!confirmed) {
        return;
    }

    removeTypingIndicator();

    $("#chatMessages").html(
        '<div id="chatbotWelcomeMessage" ' +
             'class="chat-message bot-message">' +

            '<div class="message-avatar">' +
                '<i class="fas fa-robot"></i>' +
            '</div>' +

            '<div class="message-wrapper">' +

                '<div class="message-bubble">' +

                    '<div id="chatbotWelcomeContent" ' +
                         'class="chatbot-welcome">' +
                    '</div>' +

                '</div>' +

                '<div class="message-time">' +
                    getCurrentTime() +
                '</div>' +

            '</div>' +

        '</div>'
    );

    renderDynamicWelcomeMessage();

    $("#chatQuestion")
        .val("")
        .focus();

    resizeQuestionInput();
    scrollChatToBottom();
}
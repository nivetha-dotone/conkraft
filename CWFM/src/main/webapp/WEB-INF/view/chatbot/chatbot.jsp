<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="conkraftChatBot"
     class="conkraft-chatbot"
     data-user-name="" default='User'/>"
     <input type="hidden" id="firstName" value="${firstName}">

    <!-- Header -->
    <div class="chatbot-header">

        <div class="chatbot-header-left">

            <div class="chatbot-avatar">
                <i class="fas fa-robot"></i>
            </div>

            <div class="chatbot-heading">

                <div class="chatbot-title">
                    Conkraft AI Assistant
                </div>

                <div class="chatbot-status">
                    <span class="chatbot-status-dot"></span>
                    Online
                </div>

            </div>

        </div>

        <div class="chatbot-header-right">

            <div class="chatbot-language-wrapper">

                <i class="fas fa-globe"></i>

                <select id="chatbotLanguage"
                        class="chatbot-language-select"
                        title="Select voice language">

                    <option value="en-IN">English</option>
                    <option value="hi-IN">हिन्दी</option>
                    <option value="ta-IN">தமிழ்</option>
                    <option value="te-IN">తెలుగు</option>
                    <option value="gu-IN">ગુજરાતી</option>
                    <option value="kn-IN">ಕನ್ನಡ</option>
                    <option value="ml-IN">മലയാളം</option>
                    <option value="mr-IN">मराठी</option>
                    <option value="bn-IN">বাংলা</option>
                    <option value="pa-IN">ਪੰਜਾਬੀ</option>
                    <option value="ur-IN">اردو</option>

                </select>

            </div>

            <div class="chatbot-header-actions">

                <button type="button"
                        id="clearChatButton"
                        class="chatbot-header-button"
                        title="Clear conversation">
                    <i class="fas fa-trash-alt"></i>
                </button>

                <button type="button"
                        id="minimizeChatBotButton"
                        class="chatbot-header-button"
                        title="Minimize">
                    <i class="fas fa-minus"></i>
                </button>

                <button type="button"
                        id="maximizeChatBotButton"
                        class="chatbot-header-button"
                        title="Maximize">
                    <i class="fas fa-expand"></i>
                </button>

                <button type="button"
                        id="closeChatBotButton"
                        class="chatbot-header-button"
                        title="Close">
                    <i class="fas fa-times"></i>
                </button>

            </div>

        </div>

    </div>

    <!-- Messages -->
    <div id="chatMessages"
         class="chatbot-messages"
         aria-live="polite"
         aria-label="Chat messages">

        <div id="chatbotWelcomeMessage"
             class="chat-message bot-message welcome-message">

            <div class="message-avatar">
                <i class="fas fa-robot"></i>
            </div>

            <div class="message-wrapper">

                <div class="message-bubble welcome-bubble">

                    <div id="chatbotWelcomeContent"
                         class="chatbot-welcome">
                    </div>

                </div>

                <div class="message-time">
                    Just now
                </div>

            </div>

        </div>

    </div>

    <!-- Keep your existing suggestions and input sections here -->

</div>

    <!-- Horizontal suggestions -->
    <div id="chatSuggestions"
         class="chatbot-suggestions">

        <button type="button"
                class="suggestion-button"
                data-question="Pending Approvals">
            Pending Approvals
        </button>

        <button type="button"
                class="suggestion-button"
                data-question="Active Contractors">
            Active Contractors
        </button>

        <button type="button"
                class="suggestion-button"
                data-question="Today's Gatepasses">
            Today's Gatepasses
        </button>

        <button type="button"
                class="suggestion-button"
                data-question="Work Orders">
            Work Orders
        </button>

        <button type="button"
                class="suggestion-button"
                data-question="Principal Employers">
            Principal Employers
        </button>

        <button type="button"
                class="suggestion-button"
                data-question="License Expiry">
            License Expiry
        </button>

        <button type="button"
                class="suggestion-button"
                data-question="Search Contractor">
            Search Contractor
        </button>

    </div>

    <!-- Input -->
    <div class="chatbot-input-area">

        <button type="button"
                id="voiceButton"
                class="chatbot-action-button voice-button"
                title="Speak">

            <i class="fas fa-microphone"></i>
        </button>

        <div class="chatbot-input-wrapper">

            <textarea id="chatQuestion"
                      class="chatbot-input"
                      rows="1"
                      maxlength="500"
                      placeholder="Ask something about Conkraft..."
                      autocomplete="off"></textarea>

        </div>

        <button type="button"
                id="sendChatButton"
                class="chatbot-action-button send-button"
                title="Send">

            <i class="fas fa-paper-plane"></i>
        </button>

    </div>

</div>
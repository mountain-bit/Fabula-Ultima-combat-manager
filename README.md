# Fabula Ultima Combat Manager

**Fabula Ultima Combat Manager** to aplikacja mobilna na system Android stworzona z myślą o Mistrzach Gry (Game Masters) prowadzących sesje w japońskim systemie RPG (TTJRPG) *Fabula Ultima*. Aplikacja służy do łatwego, szybkiego i przejrzystego śledzenia oraz zarządzania statystykami przeciwników podczas potyczek.

## 📌 O projekcie
Gra Fabula Ultima charakteryzuje się dynamiczną walką, w której ważną rolę odgrywają typy obrażeń, wrażliwości, efekty statusów oraz zarządzanie atrybutami. Ta aplikacja eliminuje potrzebę ciągłego używania papieru i ołówka poprzez automatyzację matematyki i wygodny interfejs użytkownika zaprojektowany w podejściu **Mobile-First** (ze zintegrowanym trybem ciemnym).

Projekt został stworzony w **Kotlinie** przy wykorzystaniu nowoczesnego frameworka UI **Jetpack Compose** oraz lokalnej bazy danych **Room**, co gwarantuje płynność i bezpieczeństwo danych - po zamknięciu aplikacji żaden postęp walki nie zostanie utracony.

## ✨ Główne założenia i funkcjonalności

*   **Zarządzanie wieloma walkami (Multi-battle)**: Twórz osobne "sceny" walki i przełączaj się między nimi w dowolnym momencie.
*   **Kompleksowe śledzenie wrogów**:
    *   Monitorowanie obecnych i maksymalnych Punktów Życia (HP) oraz Punktów Many (MP).
    *   Przypisanie odpowiednich Kości Atrybutów (D6, D8, D10, D12) dla zręczności (DEX), wnikliwości (INS), siły (MIG) i siły woli (WLP).
*   **Automatyczny Kalkulator Obrażeń**:
    *   Aplikacja automatycznie oblicza faktyczne rany na podstawie typu zadanych obrażeń.
    *   Uwzględnia mechaniki Fabula Ultima: **Vulnerability** (słabość), **Resistance** (odporność), **Immunity** (niewrażliwość) oraz **Absorption** (leczenie z obrażeń).
*   **Statusy (Status Effects)**: Szybkie nakładanie i monitorowanie efektów takich jak *Poisoned, Enraged, Dazed, Weak, Slow, Shaken*.
*   **Umiejętności (Skills)**: Możliwość dodawania własnych umiejętności oraz notatek do poszczególnych wrogów w celu ułatwienia prowadzenia walki.
*   **Persystencja danych (Zapis)**: Wszystkie stany są na bieżąco zapisywane. Zamknięcie lub zminimalizowanie aplikacji nie resetuje przebiegu potyczki.

## 🛠 Stack technologiczny

*   **Język**: Kotlin
*   **UI**: Jetpack Compose (Material Design 3)
*   **Baza Danych**: Room Database
*   **Architektura**: MVVM (Model-View-ViewModel) oparta na Kotlin Coroutines i StateFlow.
*   **Wymagania systemowe**: Android (Min SDK: 28, Target SDK: 36)

## 🚀 Uruchomienie projektu

1. Sklonuj repozytorium na swój komputer:
   ```bash
   git clone <adres-repozytorium>
   ```
2. Otwórz projekt w programie **Android Studio**.
3. Zsynchronizuj projekt z plikami Gradle.
4. Uruchom projekt (Run) na fizycznym urządzeniu z systemem Android lub emulatorze.

## 🤝 Wkład (Contributing)
Jeśli masz pomysł na nową funkcjonalność (np. inicjatywa, generowanie wrogów, eksport walk), zachęcam do tworzenia zgłoszeń (Issues) lub Pull Requestów!

# Benutzung meiner Api:

Ihr müsst euch eigentlich um fast gar nix kümmern: Die relevanten Klassen liegen im Package Models. Bilder sind noch nicht bei meinen Queries unterstützt.

## public class Quiz
 
Neuen Quiz anlegen:

### Quiz quiz= new Quiz(int id);

Holt einen Quiz mit der id "id" aus dem Server.

### Quiz.pullQuizzes() KLASSENMETHODE !!!

Wird NIEMALS über die instanzierung eines Quiz Objektes aufgerufen.
lädt eine Liste von Quizzes, die von der Datenbank geholt wird. Page gibt an welche ids wir haben wollen. Wenn zum Beispiel page=1... sucht es von 0-19... wenn page=2 sucht es von 20-39.Die Zahl kann auch anders sein.

### boolean quiz.isCorrect(int AnswerID);

Übergibt die ID der ausgewählten Antwort bei der Frage die gerade beantwortet wird, an diese Funktion. Sie wertet die punkte aus, tut sie speichern und tut den Streak erhöhen falls AnswerID richtig ist.
Zusätzlich ruft diese Methode eine private Funktion, die sich darum kümmert das mit aufsteigendem Streak die Punkte, die man von der Frage erlangt mit einem Faktor 1.03^x
multipliziert werden. x ist dabei der streak, der erreicht wird.

 
### int quiz.getStreak();


gibt den Streak ( wie viele Antworten nacheinander ihr richtig beantwortet habt zurück).


### quiz.setThumbnail();

Holt das Hintergrundbild für den Quiz von der DB. Nur Verwenden, wenn die Bilder tatsächlich zu sehen sein sollen. In einer Schleife sollte man nicht mehr als 20 mal diese Methode anwenden...

Ein bild hat etwa 1mb.. bei 20 Bildern würde es schon etwa 5 Sekunden dauern, die zu laden.

### quiz.removeThumbnail();

Löscht ein Thumbnail, könnte vielleicht nötig sein, weil man viele Bilder in den Arbeitsspeicher reinlädt.

### quiz.start();

Lädt die Erste Frage , auch mit dem Bild. Vielleicht kommt später weitere Funktionalität dazu, aber ich glaube die sollte im Frontend implementiert sein.

### quiz.nextQuestion();

Legt den Fokus des Quizzes auf die nächste Frage.

### quiz.export();

Exportiert die id und die gewonnenen Punkte an den Server. Verwenden, nur wenn man den Quiz abschließt.

## public class Me

Klasse des eingeloggten Benutzers

### loadMyQuizes()

lädt alle Quizes vom Benutzer die im Server gespeichert sind

### loadCurrentlyEdited()

lädt den Quiz vom Benutzer, den der Benutzer erstellt.

### loadMyHighScores()

lädt einige Highschores vom Benutzer.

### boolean login(username,password)

loggt den user mit username und password ein. Und gibt zurück ob der login erfolgreich war.

### boolean register(username,password)

registriert den benutzer und gibt einen boolean zurück, ob dies erfolgreich war

### boolean logout()

naja ihr wisst schon

### boolean deleteAccount()

klar oder ?

## public class Question

Die Getter in dieser Klasse werden für euch wichtig sein. Bei unklarheiten kann ich die Dokumentation ergänzen.

### public void setTStart();

setzt die Anfangszeit des Countdowns für die Funktion die die Punkte auswertet. Ihr solltet dies NACH dem Laden der graphischen Elemente einer Question betätigen.

## public class QuizBuilder

benutzt wenn der benutzer einen neuen Quiz anlegen will. Speichert sachen automatisch.

### setName()

setzt den namen des Quizzes, belibig oft ausführbar.

### newQuestion()

erstellt eine neue, komplett leere Frage

### editQuestionName(String name, int index)

setzt den Namen einer Frage an der Stelle index auf name.

### editAnswerName(String name, int questionIndex, int answerIndex)

setzt die antwort der answerIndexten Frage( frage 0 , frage 1 ,frage 2 ,frage 3... answerIndex kann deshalb Werte von 0-3 annehmen) an der Stelle questionIndex auf name.

### newAnswer(String name, int questionIndex)

erstellt eine neue Antwort mit dem namen name bei der questionIndexten Frage.

### editQuestionThumbnail( String thumbnail, int questionIndex)

diese Methode gibt der Frage an der Stelle questionIndex eine URI zu einem thumbnail.

### addCorrectAnswer(int questionIndex, int answerIndex)

diese Methode entscheidet bei der Frage an der Stelle questionIndex, welche antwort Richtig ist. Der index der richtigen Antwort ist mit answerIndex bekennzeichnet (answer 0, answer 1, answer 2, asnwer 3)

### export()

diese Methode exportiert den quiz





## Benutzung meiner Api:

Ihr müsst euch eigentlich um fast gar nix kümmern:
 
Neuen Quiz anlegen:

### Quiz quiz= new Quiz(int id);

Holt einen Quiz mit der id "id" aus dem Server.

### quiz.setThumbnail();

Holt das Hintergrundbild für den Quiz von der DB. Nur Verwenden, wenn die Bilder tatsächlich zu sehen sein sollen. In einer Schleife sollte man nicht mehr als 20 mal diese Methode anwenden...

Ein bild hat etwa 1mb.. bei 20 Bildern würde es schon etwa 5 Sekunden dauern, die zu laden.

### quiz.removeThumbnail();

Löscht ein Thumbnail, könnte vielleicht nötig sein, weil man viele Bilder in den Arbeitsspeicher reinlädt.

### quiz.start();

Lädt die Erste Frage , auch mit dem Bild. Vielleicht kommt später weitere Funktionalität dazu, aber ich glaube die sollte im Frontend implementiert sein.

### quiz.nextQuestion();

Legt den Fokus des Quizzes auf die nächste Frage und summiert die gewonnen Punkte.

### quiz.export();

Exportiert die id und die gewonnenen Punkte an den Server. Verwenden, nur wenn man den Quiz abschließt.



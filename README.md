Verteilte System<br>

Sequenzdiagramm

![WhatsApp Bild 2024-03-29 um 21 57 52_0c46d100](https://github.com/uiyoungkim/Verteilte-System/assets/115372518/924457c6-8fc9-4e28-9f62-93e9a1f56f61)

## Reisebuchungsprozess

1. Das **Reiseanfrage-System** sendet eine Anfrage an den **Reise-Broker** für eine Reisebuchung.
2. Der **Reise-Broker** sendet eine Anfrage an den **Message-Broker**, um die Verfügbarkeit eines Hotels zu prüfen.
3. Der **Reise-Broker** sendet ebenfalls eine Anfrage an den **Message-Broker**, um die Verfügbarkeit eines Fluges zu prüfen.
4. Der **Message-Broker** leitet die Hotelverfügbarkeitsanfrage an das **Hotelbuchungssystem** weiter.
5. Der **Message-Broker** leitet die Flugverfügbarkeitsanfrage an das **Flugbuchungssystem** weiter.
6. Das **Hotelbuchungssystem** prüft die Verfügbarkeit und sendet die Antwort (Bestätigung oder Ablehnung) zurück an den **Message-Broker**.
7. Das **Flugbuchungssystem** prüft die Verfügbarkeit und sendet die Antwort (Bestätigung oder Ablehnung) zurück an den **Message-Broker**.
8. Der **Message-Broker** empfängt beide Antworten und leitet sie an den **Reise-Broker** weiter.
9. Der **Reise-Broker** überprüft, ob beide Buchungen (Hotel und Flug) bestätigt wurden:
    - Wenn **beide Buchungen bestätigt** wurden, führt der **Reise-Broker** die Buchung durch und sendet eine Bestätigung der gesamten Reisebuchung zurück an das **Reiseanfrage-System**.
    - Wenn **nicht beide Buchungen bestätigt** wurden oder bei einem Fehler, informiert der **Reise-Broker** das **Reiseanfrage-System** über das Problem und initiiert gegebenenfalls einen **Rollback**, um alle bisher getätigten Buchungen zu stornieren.

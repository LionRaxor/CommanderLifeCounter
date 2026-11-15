# Commander Life Counter v44 — Android

Dit project bevat **v44** van de Commander Life Counter als offline Android WebView-app.

## Bouw via GitHub Actions (ook vanaf een telefoon)
1. Maak op github.com een **private** repository, bijvoorbeeld `commander-life-counter`, met branch `main`.
2. Pak deze ZIP uit en upload **de inhoud van deze projectmap** naar de hoofdmap van de repository. Let op: `.github/workflows/android-apk.yml` moet als echt bestand in de repository staan; alleen het ZIP-bestand uploaden werkt niet. Op een telefoon kan uploaden van mappen lastig zijn: gebruik eventueel een computer of een mobiele Git-client die mappen en verborgen bestanden kan committen.
3. Open de repository > **Actions** > **Bouw Android APK** > **Run workflow**. De workflow start ook bij een push naar `main`.
4. Open na een geslaagde build de workflow-run > **Artifacts** > `CommanderLifeCounter-v44-APK` en download de ZIP met `app-debug.apk`.
5. Pak de artifact-ZIP uit op je Android-telefoon en open `app-debug.apk` om te installeren. Sta indien nodig installatie uit deze bron toe.

## Belangrijk
- De debug-APK is geschikt voor eigen tests en delen met vrienden; publicatie in Play Store vraagt een release-build en signing.
- Een latere update moet met dezelfde signing key worden ondertekend om bestaande appdata te behouden. GitHub-hosted runners gebruiken doorgaans een andere debug key per build; **installeer volgende debug-builds niet als update met behoud van data**. Voor betrouwbare updates moet een vaste, veilig beheerde release signing key worden ingericht.
- Bestaande Chrome-profielen/afbeeldingen worden niet automatisch overgezet naar de app.
- De app forceert landscape, houdt het scherm wakker en toont de HTML offline. Test alle 7/8-spelerlayouts en import/opslag op je toestel.
- Dit is broncode, nog geen gebouwde APK.

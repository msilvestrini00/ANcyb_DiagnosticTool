<div align="center">

### PROGETTO DI PROGRAMMAZIONE AD OGGETTI A.A. 2021-2022
![logo](/media/images/LogoANcybDiagnosticToolRounded.png)
# ANCYBERNETICS DIAGNOSTIC TOOL 
### *Strumento di diagnostica per dispositivi marini per ricezione dei dati di bordo e valutazione della situazione meteorologica marina*

</div>

## INDICE

* [Introduzione](#intro)

* [Componenti e strumenti utilizzati](#compstrum)
	* [Robot marino](#robot)
	* [REST API](#api)
	* [MQTT](#mqtt)
	* [Rotte](#rotte)
	* [Datalogger](#datalogger)
	* [Interfaccia utente](#interfaccia)
	
* [Rotte](#rotte)
	* [/{macAddr}/forecast](#rotta1)
	* [/{macAddr}/forecast/filter](#rotta2)
	* [/{macAddr}/forecast/stats](#rotta3)
	* [/{macAddr}/device/last](#rotta4)
	* [/{macAddr}/device/all](#rotta5)
	* [/{macAddr}/device/stats](#rotta6)
* [Filtri](#filtri)

* [Stats](#stats)

* [UML](#uml)

* [Dimostrazione di funzionamento](#demo)
	* [Test amministratore](#testadmin)
	* [Test real-time](#testrealtime)
	
* [Eccezioni](#eccezioni)

* [Test](#test)

* [Sviluppi futuri](#sviluppifuturi)

* [Autori](#autori)



<a name="intro"></a>
## INTRODUZIONE 

### Contesto

Il progetto è stato proposto come frutto della collaborazione con uno spinoff accademico i cui sviluppatori sono soci.
Quest'ultimo è chiamato **ANcybernetics S.r.l.** e si occupa dello sviluppo di un robot pesciformi a servizio dell'istruzione e della ricerca.

### Obiettivo 🎯

In un ipotetico futuro in cui i dispositivi prodotti possano navigare autonomamente, si è reso necessario un sistema di controllo e diagnostica capace di monitorare i parametri principali di ogni robot: il progetto sviluppato si presta come soluzione ideale a questa problematica.

### Descrizione

Il progetto è un'applicazione Springboot scritta in linguaggio **Java**,  che consente di:
- raccogliere i vari dati di diagnostica e le coordinate geografiche da ogni dispositivo in circolazione
- ordinare i dati in un database e memorizzarli in locale tramite un servizio di datalogging
- raccogliere dal web i dati di previsione meteorologica relativi alle coordinate dei dispositivi 
- rendere disponibile all'utente la visualizzazione di questi dati attraverso varie modalità, permettendo di filtrarli o di eseguire statistiche su di essi.

<a name="compstrum"></a>
## COMPONENTI E STRUMENTI UTILIZZATI

Per un'approccio concettuale più concreto, di seguito si descrivono i macroblocchi hardware e software utilizzati durante lo sviluppo del progetto, partendo dalla sorgenti di dati per arrivare all'utente.

___

<a name="robot"></a>
### Robot marino

Per aggiungere un grado di difficoltà al progetto, si è supposto che l'applicativo comunichi con dispositivi appartenenti a tre ipotetiche versioni, che si differenziano leggermente per l'hardware al loro interno.

Tutti i robot sono composti principalmente da:
- un sistema di propulsione motorizzata
- uno scomparto hardware, caratterizzato da un microcontrollore e la sensoristica

Il dispositivo è dotato di un microcontrollore ESP32, il quale viene programmato tramite [MicroPython](https://micropython.org/).

Invece, per quanto riguarda i sensori sfruttati:
| Versione | Sensoristica  |
| ------ | ------ |
| ANcybFish Ver_G  | GPS         |
| ANcybFish Ver_P  | Pressione   |
| ANcybFish Ver_GT | GPS + Temperatura |

L'intero hardware è costituito da schede modulabili: questo lo rende estremamente facile da montare e/o intercambiare all'interno dello scomparto del dispositivo.

Infine, il software da implementare nel dispositivo è consultabile a questo [link](/M5Flow_files/ANcyb_firmwareGPSpub(VerG)_micropython.txt).

___

<a name="api"></a>
### REST API 

#### Descrizione

Il servizio di previsione meteorologica marina è fornito dalla REST API [Storm Glass](https://rapidapi.com/ManniskaMaskin/api/storm-glass/). 

Essa si distingue dalle altre simili per la praticità d'uso e l'attendibilità:
- I dati si estendono fino a sette giorni successivi da quello corrente, con risoluzione oraria
- Il servizio è disponibile tramite un'unica rotta, che prende come parametri le coordinate di cui si cercano le previsioni 
- E' possibile ottenere 23 tipi di dato differenti sulla situazione meteorologica
- I dati ricevuti derivano da molteplici sorgenti

#### Utilizzo

Dopo essersi iscritti al sito si riceve l'API key, tramite la quale è possibile utilizzare la rotta scritta in questo modo:

` https://stormglass.p.rapidapi.com/forecast?rapidapi-key={ APIkey }&lat={ latitudine }&lng={ longitudine } `

Su Postman la configurazione risulta la seguente:

![API](/media/images/screen%20rotte/rottaAPI.png)

Latitudine e longitudine vanno scritte in formato **Gradi decimali (DDD)**: un esempio è *41.890218, 12.492434*

Una volta richiamata la rotta, si riceve una struttura di oggetti JSON che contengono i dati meteorologici.

#### Metadati

A seguito di una chiamata eseguita il giorno 19 Gennaio, si ricevono i seguenti metadati:

```json
"meta": {
        "end": "2022-01-29 00:00",
        "lat": 43.57517,
        "lng": 13.587715,
        "params": [
            "waterTemperature",
            "wavePeriod",
            "waveDirection",
            "waveDirection",
            "waveHeight",
            "windWaveDirection",
            "windWaveHeight",
            "windWavePeriod",
            "swellPeriod",
            "swellDirection",
            "swellHeight",
            "windSpeed",
            "windDirection",
            "airTemperature",
            "precipitation",
            "gust",
            "cloudCover",
            "humidity",
            "pressure",
            "visibility",
            "seaLevel",
            "currentSpeed",
            "currentDirection"
        ],
        "start": "2022-01-19 00:00"
    }
```

*ATTENZIONE: i metadati riportati sopra dichiarano che le previsioni disponibili si estendono per dieci giorni, ma per motivi di attendibilità nell'applicativo il servizio è stato ristretto a sette.*

___

<a name="mqtt"></a>
### MQTT 

#### Broker
Lo scambio dei dati tra applicativo e dispositivi avviene tramite protocollo di comunicazione **MQTT**.

Viste le condizioni di lavoro e la finalità didattica del progetto è stato sfruttato come broker un server chiamato [MQTTHQ](https://mqtthq.com/): un webserver online pubblico destinato proprio a test di sistemi IoT. Il broker offre inoltre un utile [client](https://mqtthq.com/client) integrato che permette di gestire e simulare subscribe e publish (le funzionalità sono meglio descritte [tutorial](#)).

| MQTTHQ Broker Settings | *Valori* |
|---|---|
| Broker URL | public.mqtthq.com |
| TCP Port | 1883 |
| WebSocket Port | 8083 |
| WebSocket Path | /mqtt |

#### Publish (dispositivi ancyb)
I vari dispositivi sottomarini effettuano regolarmente dei publish al topic "ANcybDiagnosticTool" tramite i quali inviano messaggi che rispettano una certa sintassi compresa poi dell'applicativo che poi effettua l'opportuna modellazione (vedi dataManager).

#### Subscribe (applicativo)
L'applicativo è in costante ascolto grazie al subscribe allo stesso topic "ANcybDiagnosticTool" e così riceve i messaggi pubblicati sul topic da ciascun dispositivo.

Il subscribe viene configurato nel `main` del programma tramite il costruttore del MQTT client.

```java
ANcybMqttClient mqttClient = new ANcybMqttClient();
```
```java
package it.univpm.ancyb_diagnosticTool.mqtt.mqttClient;

public ANcybMqttClient() throws MqttException {
        ANcybMqttClient.getInstance();
        ancybDataManager = new ANcybDataManager();
        dataLog = new DataLogger();
        this.subscribe("ANcybDiagnosticTool");
    }
```

Di seguito le configurazioni dei client:

| Proprietà | ancybDiagnostiTool | dipositivi |
| ------ | ------ | ------ |
| ClientID | spring-server-ancyb-(*data e ora all'avvio*) | ancybFish-(*MAC address*) |
| keepalive | 60 (*non implementato il publisher*) | 300 |
| user | *nessun username* | *nessun username* |
| pass | *nessuna password* | *nessuna password* |
<a name="notaSubscribe"></a>
**NOTA:** *il publish dell'applicativo non è stato implementato per motivi di sintesi del progetto.
Per una comunicazione da applicativo a dispositivo sarebbe stato possibile sfruttare un topic personalizzato in base all'indirizzo MAC (in quanto univoco) per ciascun robot.

```
ANcybDiagnosticTool/a4:cf:12:76:76:95
```

#### Gestione messaggi MQTT

I messaggi inviati dai dispositivi sottomarini e ricevuti tramite il subscribe dell'applicativo sono stringhe che rispettano una precisa struttura.

Ciascuna stringa è composta da sottostringhe intervallate da spazi, ogni componente determina le caratteristiche del dispositivo che invia il messaggio o i dati provenienti dalla sensoristica.

**Esempio** di messaggio ricevuto da un ANcybFish Ver_G.
```java
"a4:cf:c2:7f:76:45 Ver_G 16:10:45 5189.5102N 10035.2629W 1"
```
La gestione di questa stringa è affidata al metodo `createDataObj` della classe `ANcybDataManager`.
```java
ANcybDataManager ancybDataManager = new ANcybDataManager();
DataReceived data = ancybDataManager.createDataObj(strReceived);
```
Viene restituita un'istanza di tipo `ANcybFishData`. Da questa superclasse ereditano gli attributi le strutture che descrivono i dati ricevuti dai dispositivi di diverse versioni. Questo metodo elabora quindi la stringa, identifica la versione e richiama il rispettivo costruttore.

Nel caso vengano ricevute stringhe incompatibili viene lanciata un'eccezione di tipo [MqttStringMismatch](#MqttStringMismatch)

| ANcybFishData | descrizione | 
| ---- | ---- |
| `String` **time** | Orario di invio del messaggio. |
| `String` **date** | Data di ricezione del messaggio. |
| `String` **ver** | Stringa contenente la versione del dispositivo/software. |
| `String` **macAddr** | MAC address del dispositivo da cui arriva il messaggio. |
<br/>

| ANcybFishData_VerG | descrizione | 
| ---- | ---- |
| `String` **time** | Orario di invio del messaggio. |
| `String` **date** | Data di ricezione del messaggio. |
| `String` **ver** | Stringa contenente la versione del dispositivo/software. |
| `String` **macAddr** | MAC address del dispositivo da cui arriva il messaggio. |
| `float` **latitute** | Latitudine in formato DD (Decimal Degrees). |
| `float` **longitude** | Longitudine in formato DD (Decimal Degrees). |
| `String` **qualPos** | Qualità del segnale GPS |

**Esempio** *di messaggio ricevuto da un ANcybFish Ver_G.*
```java
"a4:cf:12:76:76:95 Ver_G 16:05:45 4334.5102N 01335.2629E 1"
```

| ANcybFishData_VerG | descrizione | 
| ---- | ---- |
| `String` **time** | Orario di invio del messaggio. |
| `String` **date** | Data di ricezione del messaggio. |
| `String` **ver** | Stringa contenente la versione del dispositivo/software. |
| `String` **macAddr** | MAC address del dispositivo da cui arriva il messaggio. |
| `float` **latitute** | Latitudine in formato DD (Decimal Degrees). |
| `float` **longitude** | Longitudine in formato DD (Decimal Degrees). |
| `String` **qualPos** | Qualità del segnale GPS |
| `float` **temp** | Temperatura (in gradi Celsius) misurata dal sensore integrato nel rispettivo dispositivo. |

**Esempio** *di messaggio ricevuto da un ANcybFish Ver_GT.*
```java
"b4:cf:12:76:76:95 Ver_GT 16:05:50 4031.3926N 07401.3875W 1 10.5"
```

*(non implementata)*
| ANcybFishData_VerP | descrizione |
| ---- | ---- |
| `String` **time** | Orario di invio del messaggio. |
| `String` **date** | Data di ricezione del messaggio. |
| `String` **ver** | Stringa contenente la versione del dispositivo/software. |
| `String` **macAddr** | MAC address del dispositivo da cui arriva il messaggio. |
| `Double` **pressure** | Pressione (in Pascal) misurata dal sensore integrato nel rispettivo dispositivo. |

**Esempio** *di messaggio ricevuto da un ANcybFish Ver_P.*
```java
"b4:cf:12:76:76:95 Ver_GT 16:05:50 101325"
```

**NOTA:** *Le coordinate ricevute via stringa sono in formato DDM (gradi e minuti decimali), le conversioni in formato DD vegnono effettuate tramite opportuni metodi implementati dalla classe `Coord`. La conversione in DD è necessario per l'utilizzo dell'API esterna. (!!LIIINKKK!!)*
**NOTA:** *La gestione delle conversioni di tipo o di formato dei dati ricevuti è gestito interamente dai costruttori.*

Di seguito è riportata la gerarchia delle classi.

![eredita](/media/images/Eredità_ANcybFishData.png)

___

<a name="datalogger"></a>
### DataLogger

I dati ricevuti da MQTT e istanziati correttamente vengono stampati su un file di testo che viene creato ed è associato esclusivamente alla sessione corrente.
La gestione di ciò è destinata alla classe `DataLogger`.

Vedi un esempio di DataLogger [qui](/TestDataLogger/2022.01.19_21.21.01_DataLogger.txt).

___

<a name="interfaccia"></a>
### Interfaccia utente 

Il progetto può essere sfruttato tramite un qualsiasi browser, ma in fase di testing e di sviluppo è risultato ottimo l'utilizzo di [Postman](https://www.postman.com/).
Quest'ultimo è uno strumento di testing di API che possiede numerose features, tra cui la possibilità di salvare le rotte chiamate e di tabulare i dati in ricezione.
Pertanto, almeno in una fase primaria è consigliato l'utilizzo di questo software.


## ROTTE 

Di seguito sono elencate le rotte disponibili corredate delle relative descrizioni.
Alcune note comuni:
- Per ovvi motivi progettuali, le rotte sono fortemente legate ai robot marini. Per questo motivo esse si basano sull'indirizzo MAC del dispositivo selezionato.
- Al fine di normalizzare i dati in uscita dall'applicativo, tutte le rotte restituiscono un oggetto JSON.

| | Tipo | Rotta | Descrizione
----- | ------------ | -------------------- | ----------------------
1 | ` GET ` | [`/{macAddr}/forecast`](#rotta1) | restituisce la situazione meteo sulla posizione del dispositivo
2 | ` POST ` | [`/{macAddr}/forecast/filter`](#rotta2) | restituisce la previsione meteo oraria selezionata sulla posizione del dispositivo.
3 | ` POST ` | [`/{macAddr}/forecast/stats`](#rotta3) | restituisce le statistiche meteorologiche sulla posizione del dispositivo.
4 | ` POST ` | [`/{macAddr}/device/last`](#rotta4) | restituisce l'ultima istanza di dati di bordo inviati dal dispositivo.
5 | ` POST ` | [`/{macAddr}/device/all`](#rotta5) | restituisce lo storico delle istanze di dati di bordo inviati dal dispositivo.
6 | ` POST ` | [`/{macAddr}/device/stats`](#rotta6) | restituisce tutte le statistiche disponibili sui dati di bordo del dispositivo.

____

<a name="rotta1"></a>
### 1. */{macAddr}/forecast*

#### Esempio di input
![rotta1](/media/images/screen%20rotte/rotta1.png)

#### Dati ricevuti

```json
{
    "WaveHeight": 0.19,
    "macAddress": "a4:cf:12:76:76:95",
    "CurrentDirection": 191.31,
    "Latitude": 43.575226,
    "Time": "2022-01-19T16:00:00+00:00",
    "Longitude": 13.587703
}
```

#### Filtri e stats

- [FilterForecastByTime](#filtro1)
- [FilterObjByMac](#filtro3)

___

<a name="rotta2"></a>
### 2. */{macAddr}/forecast/filter*

#### Esempio di input
![rotta2](/media/images/screen%20rotte/rotta2.png)

#### Dati ricevuti

```json
{
    "WaveHeight": 0.13,
    "macAddress": "a4:cf:12:76:76:95",
    "CurrentDirection": 0.0,
    "Latitude": 43.575226,
    "Time": "2022-01-26T00:00:00+00:00",
    "Longitude": 13.587703
}
```

#### Filtri e stats

- [FilterForecastByTime](#filtro1)
- [FilterObjByMac](#filtro3)

___

<a name="rotta3"></a>
### 3. */{macAddr}/forecast/stats*

#### Esempio di input
![rotta3](/media/images/screen%20rotte/rotta3.png)

#### Dati ricevuti

```json
{
    "Stats": [
        {
            "Stats data": {
                "macAddress": "a4:cf:12:76:76:95",
                "Latitude": 43.575226,
                "Time": "2022-01-19T16:00:00+00:00",
                "End": "2022-01-26T16:00:00+00:00",
                "Longitude": 13.587703
            }
        },
        {
            "Stats values": {
                "Current Direction": "39,49",
                "Wave Height": "0,43"
            }
        }
    ]
}
```

#### Filtri e stats

- [AverageCurrentDirection](#stats1) 
- [AverageWaveHeight](#stats2) 
- [FilterObjByMac](#filtro3)

___

<a name="rotta4"></a>
### 4. */{macAddr}/device/last*

#### Esempio di input
![rotta4](/media/images/screen%20rotte/rotta4.png)

#### Dati ricevuti

```json
{
    "Mac address": "a4:cf:12:76:76:95",
    "Version": "Ver_G",
    "Quality position": "1",
    "Time": "16:07:00",
    "Latitude": 43.575226,
    "Longitude": 13.587703,
    "Date": "2022.01.19"
}
```

#### Filtri e stats

- [FilterObjByMac](#filtro3)

___

<a name="rotta5"></a>
### 5. */{macAddr}/device/all*

#### Esempio di input
![rotta5](/media/images/screen%20rotte/rotta5.png)

#### Dati ricevuti

```json
[
    {
        "time": "16:05:45",
        "date": "2022.01.19",
        "ver": "Ver_G",
        "macAddr": "a4:cf:12:76:76:95",
        "latitude": 43.57517,
        "longitude": 13.587715,
        "qualPos": "1",
        "coord": [
            43.57517,
            13.587715
        ]
    },

    [...]

    {
        "time": "16:07:00",
        "date": "2022.01.19",
        "ver": "Ver_G",
        "macAddr": "a4:cf:12:76:76:95",
        "latitude": 43.575226,
        "longitude": 13.587703,
        "qualPos": "1",
        "coord": [
            43.575226,
            13.587703
        ]
    }
]
```

#### Filtri e stats

- [FilterListByMac](#filtro2)

___

<a name="rotta6"></a>
### 6. */{macAddr}/device/stats*

#### Esempio di input
![rotta6](/media/images/screen%20rotte/rotta6.png)

#### Dati ricevuti

```json
{
    "Mac address": "a4:cf:12:76:76:95",
    "Stats results": {
        "Geodetic distance": "6,44 m"
    }
}
```

#### Filtri e stats

- [FilterListByMac](#filtro2)
- [AverageTemperatureFish](#stats3)
- [GeodeticDistance](#stats4)

<a name="filtri"></a>
## FILTRI

Nel progetto sono stati implementati dei **filtri**, ovvero delle classi che si occupano di selezionare determinate istanze o variabili all'interno di un insieme noto, a seconda di un determinato parametro di selezione.
Queste classi sono risultate estremamente utili dal punto di vista pratico, in quanto utilizzati in molteplici contesti (a parte nella stesura delle rotte apposite).


Come mostrato nella sezione [filtri](#filtriuml) dell'[UML](#uml), i filtri implementano un'interfaccia chiamata `FilterInterface` suddivisa in tre metodi, che compongono la loro struttura generica:

| Tipo |  Metodo |  Descrizione |
| ---   |   ---  | ---- |
|  `Object` | `getDataToFilter()` | Restituisce il dato da filtrare |
|  `void` | `computeFilter()` | Contiene il codice di filtraggio |
| ` Object` | `getFilteredData()` | Restituisce il dato filtrato. |


I filtri implementati sono descritti in tabella:

| Filtro |  Descrizione |
| ---  | ---- |
| <a name="filtro1"></a> `FilterForecastByTime` | Restituisce l'istanza di previsione meteo a seconda del tempo inserito |
| <a name="filtro2"></a>`FilterListByMac` | Restituisce una collezione di istanze di dati di bordo in base all'indirizzo MAC  |
| <a name="filtro3"></a>`FilterObjByMac` | Restituisce l'ultima istanza di dati di bordo in base all'indirizzo MAC |

<a name="stats"></a>
## STATS

In modo simile ai filtri, l'applicativo sfrutta delle classi per eseguire delle **statistiche** sui dati. 
Esse consistono nel prelevare determinate istanze per crearne altre, le quali contengono dei valori medi: grazie a questo meccanismo, il cliente ha la capacità di monitorare l'andamento di determinate grandezze, che siano relative al passato (dati di bordo) o al futuro (previsione meteo).

Anche le statistiche implementano un'interfaccia (`StatsInterface`), che si divide in tre metodi:

| Tipo |  Metodo |  Descrizione |
| ---   |   ---  | ---- |
|  `Object` | `getDataForStats()` | Restituisce l'istanza su cui effettuare la statistica |
|  `void` | `computeStats()` | Contiene il codice di elaborazione della statistica |
| ` String` | `getStats()` | Restituisce la statistica effettuata |


Le stats implementate sono descritte in tabella:

| Stats |  Descrizione |
 | ---  | ---- |
 | <a name="stats1"></a>`AverageCurrentDirection` | Restituisce la direzione della corrente marina media, effettuata sulle istanze di previsione selezionate |
| <a name="stats2"></a>`AverageWaveHeight` | Restituisce l'altezza delle onde media, effettuata sulle istanze di previsione selezionate |
| <a name="stats3"></a>`AverageTemperatureFish` | Restituisce la temperatura media del dispositivo, effettuata sulle istanze di dati di bordo selezionate |
| <a name="stats4"></a>`GeodeticDistance` | Restituisce la distanza geodetica percorsa del dispositivo, effettuata sulle istanze di dati di bordo selezionate |


<a name="uml"></a>
## UML

![uml](/media/images/screen%20UML/UML.png)

### Applicazione

![application](/media/images/screen%20UML/application.png)

### Controller

![controller](/media/images/screen%20UML/controller.png)

### Servizi

![service](/media/images/screen%20UML/service.png)

### Modello

![model](/media/images/screen%20UML/model.png)

### MQTT

![mqtt](/media/images/screen%20UML/mqtt.png)

### Datalogger

![datalogger](/media/images/screen%20UML/datalogger.png)

<a name="filtriuml"></a>
### Filtri

![filters](/media/images/screen%20UML/filters.png)

### Statistiche

![stats](/media/images/screen%20UML/stats.png)

### Eccezioni

![exception](/media/images/screen%20UML/exception.png)

### Utilities

![utilities](/media/images/screen%20UML/utilities.png)

<a name="demo"></a>
## DIMOSTRAZIONE DI FUNZIONAMENTO 

Basandosi sulla ricezione di dati in real-time, l'applicativo dovrebbe essere testato esclusivamente se si è dotati di un dispositivo ANcybFish.

Per ovviare all'assenza di questo, sono possibli due vie per testare l'applicativo e le sue principali funzionalità.

<a name="testadmin"></a>
### Test amministratore

Nel package `DataReceived` è stata implementata la classe `Admin` il cui metodo `simulateDataReceived()` crea 18 istanze tra `ANcybFishData_VerG` e `ANcybFishData_VerGT` provenienti da 3 zone geografiche note (Ancona, Sidney e NewYork) e dispositivi diversi (3 MAC address diversi).

Il metodo è presente nel `main` e verrà azionato a seconda del `boolean` che viene passato come parametro.

Per questo, dopo aver avviato l'applicativo è possibile testare le varie rotte del programma e confrontare i risultati ottenuti da filti e stats, con quelli riportati nel file tutorial consultabile [qui](/TutorialANcybDiagnosticToolTest.txt), il tutto senza ricevere altri dati via MQTT. 

<a name="testrealtime"></a>
### Test real-time

E' possibile testare l'applicativo in tutte le sue componenti (sia rotte, che lato ricezione MQTT) utilizzando il [client](https://mqtthq.com/client) reso disponibile direttamente dal broker via browser.

In questo modo sarà possibile far ricevere all'applicazione qualsiasi messaggio desiderato, così da testare anche eccezioni e funzionalità in tempo reale.

Tutte le informazioni e le istruzioni possono essere consultate [qui](/TutorialANcybDiagnosticToolTest.txt).

<a name="eccezioni"></a>
## ECCEZIONI

Sono state create una serie di **eccezioni personalizzate** consultabili [qui](/ancyb_diagnosticTool/src/main/java/it/univpm/ancyb_diagnosticTool/Exception/).

* **WrongCoordFormat**: lanciata se è impossibile convertire una coordinata dal formato DDM al formato DD, viene visualizzato un messaggio diverso
```
WrongCoordFormat(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
<a name="MqttStringMismatch"></a>
* **MqttStringMismatch**: lanciata se la stringa ricevuta via MQTT non è elaborabile e trasformabile in un'istanza `ANcybFishData`.
```
MqttStringMismatch(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
* **FilterFailure**: lanciata se avvengono errori nell'elaborazioni con i filtri. (!!!LINKK!!)
```
FilterFailure(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
* **StatsFailure**: lanciata se avvengono errori nell'elaborazioni con le statistiche. (!!!LINKK!!)
```
StatsFailure(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
* **VersionMismatch**: lanciata per segnalare situazioni in cui la versione di un'istanza non coincide con quella attesa. (!!!LINKK!!)
```
VersionMismatch(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
* **URLIsNull**: lanciata per segnalare che l'URL non è stato generato, quindi è nullo. (!!!LINKK!!)
```
URLIsNull(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
* **InvalidParameter**: lanciata se eventuali parametri in ingresso risultano invalidi. (!!!LINKK!!)
```
InvalidParameter(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```
* **ForecastBuildingFailure**: lanciata se avviene un errore durante la costruzione di un oggetto `Forecast`. (!!!LINKK!!)
```
ForecastBuildingFailure(*CAUSA PRINCIPALE*) -> *DESCRIZIONE ESPLICITA DELLA CAUSA*
```

Nel caso di classi che avrebbero lanciato molte eccezioni diverse si è optato per creare delle macro eccezioni (come <a name="mqttstringmismatch"></a>**MqttStringMismatch**) che le potessero racchiudere in un un'unica. In tali casi verrà mostrato un messaggio del tipo
```
Exception: it.univpm.ancyb_diagnosticTool.Exception.MqttStringMismatch: MqttStringMismatch(Ver_GT constructor)
Deep Exception: it.univpm.ancyb_diagnosticTool.Exception.WrongCoordFormat: WrongCoordFormat(Latitude) -> angle not included between -90° and 90°
```

<a name="test"></a>
## TEST 

Al fine di testare l'applicativo sono stati sviluppati dei JUnit consultabili [qui]!!!LLLLIIIIINNNKK!!. Nel dettaglio:

* **Test 1:** `FilterForecastByTimeTest` testa la relativa classe `FilterForecastByTime` !!LLLLLLLLLLINIIIIIIIIINNNNNNKKKKKKK!! 
* **Test 2:** `TestMqttDataReceived` testa i vari metodi presenti nel package DataReceived, ovvero chi gestisce le stringhe inviate dai dispositivi e le conseguenti istanze `ANcybFishData`.
* **Test 3:** `FishDataManagerTest` testa i metodi di `AncybDiagnosticToolServiceImpl` tra cui i filtri !!LLLLLLLLLLINIIIIIIIIINNNNNNKKKKKKK!!  e le stats !!LLLLLLLLLLINIIIIIIIIINNNNNNKKKKKKK!! sui `ANcybFishData`.
* **Test 4:** `ForecastDataManagerTest` testa il metodo `BuildForecast` che si occupa di elabora i dati ricevuti dalla chiamata API.

<a name="sviluppifuturi"></a>
##SVILUPPI FUTURI 

A causa degli scopi prettamente didattici e le tempistiche relativamente ridotte per un progetto reale di tali dimensioni, 
il lavoro ha subito varie semplificazioni funzionali che ne giustificano la reale implementazione.

Di seguito vengono elencate alcune features che avrebbero conferito al lavoro una effettiva utilità in campo:

- Sostituzione del sistema di posizionamento subacqueo con tecnologia USBL al posto di quello GPS (per permettere l'invio dei dati in immersione)
- Implementazione di un'interfaccia web che rende l'applicativo più user-friendly:
    - integrando Google Maps per visualizzare graficamente la posizione dei dispositivi
    - visualizzando i dati descritti sopra in un'unica schermata
    - trasformando l'imput da testuale (inserimento di rotte) a grafico (pulsanti)
- Implementazione del publish da parte dell'applicativo che potrebbe quindi inviare dei messaggi a specifici topic corrispondenti ai vari dispositivi (vedi [Nota](#subscribe)). Questi messaggi inviati potrebbero, sulla base dei dati meteorologici marini, condizionare il comportamento dei dispositivi in acqua (un esempio potrebbe essere: nel caso venga previsto un forte moto ondoso far emergere il dispositivo).

<a name="autori"></a>
## AUTORI

Nome | LinkedIn | Contributo | Parti sviluppate 
---- | ---- | ---- | ------
[Giacomo Fiara](https://github.com/gfiara) | [Link](https://www.linkedin.com/in/giacomo-fiara-7733b0222/) | 50% | Configurazione dispositivo esterno, MQTT, model, filtri, stats, eccezioni, test, controller, javadoc
[Manuele Silvestrini](https://github.com/msilvestrini00) | [Link](https://www.linkedin.com/in/manuele-silvestrini-a195bb222/) | 50% | API esterna, model, filtri, stats, eccezioni, test, controller, javadoc

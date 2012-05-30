<?php

try {
    $pdo = new PDO($dsn, $user, $password);
} catch (PDOException $e) {
    echo 'Connection failed: ' . $e->getMessage();
}

$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_SILENT);
$DOWNLOAD = ":";
if (isset($_GET)) {
    if (isset($_GET["changelog"])) {
        $select = $pdo->prepare("SELECT Major, State, Build, Note, Minor FROM Versions ORDER BY Major DESC");

        if ($select->execute()) {
            $change = $select->fetchAll(PDO::FETCH_ASSOC);
            if (isset($change[0]["Major"])) {
                echo json_encode($change);
                exit;
            }
        }
        $dbh = null;


        echo json_encode(array("Error" => "Error with the change log"));
        exit;
    } else if (isset($_GET["version"])) {

        $STH = $pdo->prepare("SELECT Major, State, Build, Note, Minor FROM `Versions` WHERE 1 ORDER BY Major DESC LIMIT 0 , 1");

        if ($STH->execute()) {
            $ver = $STH->fetch(PDO::FETCH_ASSOC);
        }

        if (!isset($ver["Major"]))
            exit;

        extract($ver);

        $dbh = null;

        if ($Major <= $_GET["version"]) {
            echo json_encode(array("UPDATE" => "NOUPDATE"));
            exit;
        }

        if ($Minor > $_GET["version"]) { //Too old for even the minimum. Force update time!
            echo json_encode(array("UPDATE" => "FORCEUPDATE", "Note" => "Force update (Has to update) available [$Major]: $Note.", "Download" => $DOWNLOAD));
            exit;
        }

        if ($Major > $_GET["version"]) {
            echo json_encode(array("UPDATE" => "SOFTUPDATE", "Note" => "Update available [$Major]: $Note.", "Download" => $DOWNLOAD));
            exit;
        }
        exit;
    }
}
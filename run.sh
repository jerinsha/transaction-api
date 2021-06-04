mvn clean install

arg1=$1
arg2=$2
arg3=$3

echo "Account ID  :$1"
echo "From Date   :$2"
echo "To Date   :$3"
java -cp  target/transaction-api-1.0-SNAPSHOT.jar:*  au.coles.me.code.transactionapi.CustomerTransactionApplciation  "$1" "$2" "$3"

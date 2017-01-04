FILES=/Users/Kundjanasith/Documents/workspace/TWS_API/src/com/ib/client/*
for i in $FILES
do
  echo $i
  echo $i >> client_test.txt
  cat $i >> client_test.txt
done

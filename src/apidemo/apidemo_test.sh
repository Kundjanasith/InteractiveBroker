FILES=/Users/Kundjanasith/Documents/workspace/TWS_API/src/apidemo/*
for i in $FILES
do
  echo $i
  echo $i >> apidemo_test.txt
  cat $i >> apidemo_test.txt
done

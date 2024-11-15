# check compile and unit test
bash ./scripts/run_unit_tests.sh

if [ $? -eq 0 ]
then
  echo "Compiled and unit tests PASSED"
else
  echo "Compiled and unit tests FAILED"
  exit 1
fi

# run static analysis
bash ./scripts/run_analysis.sh

echo "complete!"

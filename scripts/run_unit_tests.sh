today=`date '+%Y_%m_%d_%H:%M:%S'`;
test=$( ant test )

# pipe unit test output to a file
echo "$test" > ./reports/unit_test/$today.unit_test_report.txt

if [[ $test == *"SUCCESSFUL"* ]]; then
	# run coverage tool
	ant report
	
	exit 0
else
	exit 1
fi
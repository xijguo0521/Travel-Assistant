today=`date '+%Y_%m_%d_%H:%M:%S'`;
warning=$(sh ./pmd-bin-6.9.0/bin/run.sh pmd -d ./src/ -f text -R rulesets/java/basic.xml)

# pipe to file
echo "$warning" > ./reports/static_analysis/$today.static_analysis.txt

if [ -z "$warning" ]; then
	exit 0
else 
	exit 1
fi


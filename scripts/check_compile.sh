sh ./scripts/rm-build.sh
compile=$( ant compile )

if [[ $compile == *"SUCCESSFUL"* ]]; then
	echo 0
else
	echo 1
fi
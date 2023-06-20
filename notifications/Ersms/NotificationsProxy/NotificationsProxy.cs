using System;
using Microsoft.Azure.Functions.Worker;
using Microsoft.Extensions.Logging;

namespace NotificationsProxy
{
    public class NotificationsProxy
    {
        private readonly ILogger _logger;

        public NotificationsProxy(ILoggerFactory loggerFactory)
        {
            _logger = loggerFactory.CreateLogger<NotificationsProxy>();
        }

        [Function("NotificationsProxy")]
        public void Run(
            [ServiceBusTrigger("notifications",
                Connection = "<FILL>")] 
            string myQueueItem)
        {
            _logger.LogInformation("C# ServiceBus queue trigger function processed message: {MyQueueItem}", myQueueItem);
        }
    }
}

